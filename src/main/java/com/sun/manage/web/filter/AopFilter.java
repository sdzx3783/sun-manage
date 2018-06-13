package com.sun.manage.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.sun.manage.common.util.ContextUtil;
import com.sun.manage.common.util.RequestContext;
import com.sun.manage.service.util.AppUtil;
//@Component (不用注解 模拟第三方过滤器加入到本项目)
public class AopFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("AppFilter filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try
     {
       ContextUtil.clearAll();
       RequestContext.setHttpServletRequest((HttpServletRequest)request);
       RequestContext.setHttpServletResponse((HttpServletResponse)response);
       
       SessionLocaleResolver sessionResolver = (SessionLocaleResolver)AppUtil.getBean(SessionLocaleResolver.class);
       Locale local = sessionResolver.resolveLocale((HttpServletRequest)request);
       ContextUtil.setLocale(local);
       
       chain.doFilter(request, response);
     }
     finally {
       ContextUtil.clearAll();
       RequestContext.clearHttpReqResponse();
     }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("AppFilter filter init");
	}

}
