package com.sun.manage.web.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.sun.manage.common.json.SmartDateEditor;



public class BaseController{
	public static final String Message = "message";

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Integer.class, (String) null,
				new CustomNumberEditor(Integer.class, (NumberFormat) null, true));
		binder.registerCustomEditor(Long.class, (String) null,
				new CustomNumberEditor(Long.class, (NumberFormat) null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new SmartDateEditor());
	}

	
	
}