package com.sun.manage.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.manage.web.ResultMessage;

import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
public class LoginController {

	/**
	 * Go login
	 * @param
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public @ResponseBody ResultMessage login(@RequestParam String account, @RequestParam String password,HttpServletRequest request, HttpServletResponse response) throws IOException {
		UsernamePasswordToken upt = new UsernamePasswordToken(account, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(upt);
		} catch (AuthenticationException e) {
			return ResultMessage.createFAIL("您的账号或密码输入错误!");
		}
		//如果要指定某个跳转url 使用下面两行代码
    	/*WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request,response,"/admin/index");
		return ResultMessage.createSuccess("登录成功!");*/
		WebUtils.redirectToSavedRequest(request, response, "/index");
		return null;
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
