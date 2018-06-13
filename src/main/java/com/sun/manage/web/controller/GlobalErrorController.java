package com.sun.manage.web.controller;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.manage.common.util.RequestUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GlobalErrorController extends BasicErrorController {

    public GlobalErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    /**
    * 定义500的ModelAndView
    * @param request
    * @param response
    * @return
    */

    @RequestMapping(produces = "text/html",value = "/500")
    public ModelAndView errorHtml500(HttpServletRequest request,HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        String ip =RequestUtil.getIpAddr(request);
        String errorUrl = getErrorUrl(request);
        String substring_errorUrl = StringUtils.substring(errorUrl, 0, 1000);
        log.info("error: errorUrl:"+errorUrl+"\n msg:"+model.get("msg")+"\n ip:"+ip);
        return new ModelAndView("error/500.html", model);
    }

    /**
    * 定义500的错误JSON信息
    * @param request
    * @return
    */

    @RequestMapping(value = "/500")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error500(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        String ip =RequestUtil.getIpAddr(request);
        String errorUrl = getErrorUrl(request);
        String substring_errorUrl = StringUtils.substring(errorUrl, 0, 1000);
        log.info("error: errorUrl:"+errorUrl+"\n msg:"+body.get("message")+"\n ip:"+ip);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }
    
    @RequestMapping(produces = "text/html",value = "/400")
    public ModelAndView errorHtml400(HttpServletRequest request,HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/400.html", model);
    }
    @RequestMapping(value = "/400")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error400(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }
    @RequestMapping(produces = "text/html",value = "/401")
    public ModelAndView errorHtml401(HttpServletRequest request,HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/401.html", model);
    }
    @RequestMapping(value = "/401")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error401(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }
    @RequestMapping(produces = "text/html",value = "/404")
    public ModelAndView errorHtml404(HttpServletRequest request,HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/404.html", model);
    }
    @RequestMapping(value = "/404")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error404(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }
	private String getErrorUrl(HttpServletRequest request){
		
		String url=request.getAttribute("javax.servlet.error.request_uri").toString();
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(url);
		Enumeration<?> e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
		
	}
}