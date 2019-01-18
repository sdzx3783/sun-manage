package com.sun.manage.web.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.manage.repository.ignite.Person;
import com.sun.manage.repository.ignite.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.manage.common.util.DateFormatUtil;
import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.web.ResultMessage;
import com.sun.manage.web.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest/test")
public class TestRestController extends BaseController{
	private static final AtomicLong ID=new AtomicLong(0L);

	@Autowired
	private PersonRepository personRepository;

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

	@RequestMapping("/testSaveIgnite")
	public void testSaveIgnite(HttpServletRequest request){
		String url=request.getRequestURL().toString();
		Long now=new Date().getTime();
		Person p=new Person(now,url,"phone");
		personRepository.save(now,p);
		System.out.println("保存ID:"+now);
		Iterable iterable=personRepository.findAll();
		Iterator iterator = iterable.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.println("========testSaveIgnite over==========");
	}
}
