package com.sun.manage.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;

import com.sun.manage.service.util.AppUtil;
@WebListener
public class StartServerListener extends ContextLoaderListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		AppUtil.init(event.getServletContext());
	}

}
