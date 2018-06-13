package com.sun.manage.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sun.manage.web.filter.AopFilter;
import com.sun.manage.web.filter.EncodingFilter;
import com.sun.manage.web.interceptor.TimeInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	@Autowired
	private TimeInterceptor timeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	}
	@Bean
	public FilterRegistrationBean timeFilter(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		AopFilter timeFilter=new AopFilter();
		filterRegistrationBean.setFilter(timeFilter);
		List<String> urls=new ArrayList<>();
		urls.add("/*");
		filterRegistrationBean.setUrlPatterns(urls);
		return filterRegistrationBean;
	}
	@Bean
	public FilterRegistrationBean encodingFilter(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		EncodingFilter encodingFilter=new EncodingFilter();
		filterRegistrationBean.setFilter(encodingFilter);
		List<String> urls=new ArrayList<>();
		urls.add("/*");
		filterRegistrationBean.setUrlPatterns(urls);
		return filterRegistrationBean;
	}
	/**
	 * 自定义异常处理器
	 * @param errorAttributes
	 * @return
	 */
	@Bean 
    public EmbeddedServletContainerCustomizer containerCustomizer(){ 
        return new EmbeddedServletContainerCustomizer(){ 
           @Override 
           public void customize(ConfigurableEmbeddedServletContainer container) { 
               container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
               container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
               container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401"));
               container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
           } 
        }; 
   } 
	
}
