package com.sun.manage.web.shiro;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.manage.entity.ShiroUser;
import com.sun.manage.web.ResultMessage;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimeZone;

/**
 * Created by huangdan on 2017/8/31.
 */
public class ShiroLoginFilter extends AdviceFilter {
    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (null == user) {
        	WebUtils.saveRequest(request);
        	ResultMessage httpResult=ResultMessage.createUnauthorized("请重新登录！");
        	writeJsonOrJumpToPage(httpResult,(HttpServletRequest)request, (HttpServletResponse)response);
            return false;
        }
        return true;
    }
    private void writeJsonOrJumpToPage(ResultMessage httpResult , HttpServletRequest req, HttpServletResponse response) throws ServletException {
        PrintWriter out = null;
        try {
        	//使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
    		String contentTypeHeader = req.getHeader("Content-Type");
    		String acceptHeader = req.getHeader("Accept");
    		String xRequestedWith = req.getHeader("X-Requested-With");
    		if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
    				|| (acceptHeader != null && acceptHeader.contains("application/json"))
    				|| "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
    			response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                ObjectMapper mapper = new ObjectMapper();
                mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
                String json =  mapper.writeValueAsString(httpResult);
                out = response.getWriter();
                out.write(json);
    		} else {
    			//req.getRequestDispatcher("/login").forward(req, response);
    			response.sendRedirect("/login");
    		}
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
}
