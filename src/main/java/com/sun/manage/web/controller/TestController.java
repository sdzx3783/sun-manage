package com.sun.manage.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.manage.common.util.DateFormatUtil;
import com.sun.manage.model.User;
import com.sun.manage.web.il8n.MessageUtil;

import lombok.extern.slf4j.Slf4j;
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController extends BaseController{
	@Autowired
	private MessageUtil messageUtil;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String testGet() {
		return "common.jsp";
	}
	/**
	 * 测试web-inf外的jsp试图访问
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/index")
	public void tesIndex(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/demo-signIn.jsp").forward(request, response);;
	}
	@RequestMapping("/thymleaf")
	public String tesThymleaf(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		return "testTemplate.html";
	}
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public User getUser() {
		User user = new User();
		try {
			user.setBirthday(DateFormatUtil.parse("1993-12-08"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setCtime(new Date());
		String text = messageUtil.getText("app.name");
		log.info("国际化文本：app.name不带参数:"+text);
		String text1 = messageUtil.getText("app.name","哈哈");
		log.info("国际化文本：app.name带参数:"+text1);
		String text2 = messageUtil.getText("app1.name","哈哈");
		log.info("国际化文本：app1.name带参数:"+text2);
		return user;
	}
}
