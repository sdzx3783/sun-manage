package com.sun.manage.web.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.manage.common.util.DateFormatUtil;
import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.web.ResultMessage;
import com.sun.manage.web.exception.BusinessException;

@RestController
@RequestMapping("/rest/test")
public class TestRestController extends BaseController{
	@RequestMapping(method=RequestMethod.GET)
	public ResultMessage testGet() {
		throw new BusinessException("业务出异常！");
		//return new ResultMessage(ResultMessage.Success, "success");
	}
	
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public SysUser getUser() {
		SysUser user = new SysUser();
		try {
			user.setBirthday(DateFormatUtil.parse("1993-12-08"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setCtime(new Date());
		return user;
	}
}
