package com.sun.manage.web.shiro;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.manage.web.ResultMessage;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

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
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
    /**
     * shiro认证perms资源失败后回调方法(该过滤器只能处理应用启动时shiro配置文件中或者shiro配置bean中的权限不匹配的处理，无法处理基于shiro权限相关注解的处理)
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        ResultMessage httpResult=ResultMessage.createUnauthorized("没有权限！");
        writeJson(httpResult,httpServletResponse);
        return false;
    }
    private void writeJson(ResultMessage httpResult , HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
            String json =  mapper.writeValueAsString(httpResult);
            out = response.getWriter();
            out.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
