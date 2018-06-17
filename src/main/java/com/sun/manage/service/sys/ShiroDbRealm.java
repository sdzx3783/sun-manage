package com.sun.manage.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.manage.common.util.Encodes;
import com.sun.manage.common.util.EncryptUtil;
import com.sun.manage.entity.MySimpleByteSource;
import com.sun.manage.entity.ShiroUser;
import com.sun.manage.entity.sys.SysMenu;
import com.sun.manage.entity.sys.SysUser;

public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	protected SysUserService systemUserService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUser user = systemUserService.getByAccount(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			ShiroUser shiroUser = new ShiroUser(user.getUserId(), user.getAccount(), user.getUserName());
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), new MySimpleByteSource(salt), getName());
		} else {
			return null;
		}
	//	return new SimpleAuthenticationInfo(new ShiroUser(100000, "admin", "admin1名字"),"9e17ea12d1452bfcb855af3a0b1b0b5abe0b40d3", new MySimpleByteSource(Encodes.decodeHex("0e40dac260ccb35e")), getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SysUser user = systemUserService.getByAccount(shiroUser.getAccount());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> list = new ArrayList<String>();
		if (user.isSuperUser()) {
			list.add("*");
		} else {
			List<SysMenu> systemMenuList = systemUserService.getMenuByUserId(user.getUserId());
			/*for (SystemMenu systemMenu : systemMenuList) {
			}*/
		}
		info.addStringPermissions(list);
		if(shiroUser!=null) {
			info.addStringPermission("manage");//登陆成功的用户 都赋予manage权限
		}
		info.addStringPermission("test:index");
		return info;
	}

	/**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher()
    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(EncryptUtil.HASH_ALGORITHM);
        matcher.setHashIterations(EncryptUtil.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
//        setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
    }

}
