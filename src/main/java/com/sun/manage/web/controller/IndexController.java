package com.sun.manage.web.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.manage.entity.ShiroUser;



@Controller
@RequestMapping("/index")
public class IndexController {
	/**
	 * Go login
	 * @param
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public String loginPage(Model model) {
		ShiroUser principal = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", principal);
		return "index.html";
	}
}
