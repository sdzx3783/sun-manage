package com.sun.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/index")
public class IndexController {
	/**
	 * Go login
	 * @param
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public String loginPage() {
		return "index.html";
	}
}
