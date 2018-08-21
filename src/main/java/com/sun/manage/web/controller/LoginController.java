package com.sun.manage.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.asssembly.weixin.config.WxConfig;
import com.sun.asssembly.weixin.config.WxConfigProperties;
import com.sun.manage.common.constant.LoginType;
import com.sun.manage.web.ResultMessage;

import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
public class LoginController {
	@Autowired
	private WxConfigProperties wxConfigProperties;
	
	/**
	 * Go login
	 * @param
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public @ResponseBody ResultMessage login(@RequestParam String account, @RequestParam String password,@RequestParam int type,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LoginType loginType = LoginType.getLoginTypeByType(type);
		if(loginType==null) {
			return ResultMessage.createFAIL("不支持的登陆方式!");
		}
		ResultMessage rm = ResultMessage.createSuccess("登陆成功");
		switch(loginType) {
			case password:{
				UsernamePasswordToken upt = new UsernamePasswordToken(account, password);
				Subject subject = SecurityUtils.getSubject();
				try {
					subject.login(upt);
				} catch (AuthenticationException e) {
					rm=ResultMessage.createFAIL("您的账号或密码输入错误!");
				}
				//如果要指定某个跳转url 使用下面两行代码
		    	/*WebUtils.getAndClearSavedRequest(request);
		        WebUtils.redirectToSavedRequest(request,response,"/admin/index");
				return ResultMessage.createSuccess("登录成功!");*/
				//WebUtils.redirectToSavedRequest(request, response, "/index");
				break;
			}
			case weixin:{
				String callbakUrl="/index.html";
				String redirect_url=new StringBuilder(WxConfig.WEB_LOGIN_CALLBACK_URL).append("?redirect_url=").append(callbakUrl).toString();
				String encode = URLEncoder.encode(redirect_url, "utf-8");
				String webLoginUrl= String.format(WxConfig.WEB_LOGIN_URL,wxConfigProperties.getAppSecret(),encode);
				System.out.println(webLoginUrl);
				rm=ResultMessage.createSuccess(webLoginUrl);
			}
		}
		return rm;
	}
	/**
	 * Go login
	 * @param
	 * @return
	 */
	@RequestMapping(value="/login", method= RequestMethod.GET)
	public String loginPage() {
		return "login.html";
	}

	/**
	 * Exit
	 * @return
	 */
	@RequestMapping(value="logout",method= RequestMethod.POST)
	public ResultMessage logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return ResultMessage.createSuccess("登出成功!");
	}




}
