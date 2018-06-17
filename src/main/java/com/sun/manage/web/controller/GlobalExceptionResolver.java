package com.sun.manage.web.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理
 * @author LinkinStar
 */
@ControllerAdvice
public class GlobalExceptionResolver extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException ex) {
		Map<String,Object> result=new HashMap<>();
		result.put("status", "401");
		result.put("message", "权限不够!");
		return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

  
}