package com.sun.manage.service.sys;

import com.sun.manage.common.util.Encodes;
import com.sun.manage.model.MySimpleByteSource;
import com.sun.manage.model.ShiroUser;
import com.sun.manage.model.SystemMenu;
import com.sun.manage.model.User;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	protected SystemUserService systemUserService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = systemUserService.findUserByAccount(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			ShiroUser shiroUser = new ShiroUser(user.getUserId(), user.getAccount(), user.getUserName());
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), new MySimpleByteSource(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = systemUserService.findUserByAccount(shiroUser.getAccount());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> list = new ArrayList<String>();
		if (user.isSuperUser()) {
			list.add("*");
		} else {
			List<SystemMenu> systemMenuList = systemUserService.getMenuByUserId(user.getUserId());
			for (SystemMenu systemMenu : systemMenuList) {
			}
		}
		info.addStringPermissions(list);
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
		matcher.setHashIterations(1024);
		setCredentialsMatcher(matcher);
		// setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
	}

}
