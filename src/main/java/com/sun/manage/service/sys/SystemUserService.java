package com.sun.manage.service.sys;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sun.manage.model.SystemMenu;
import com.sun.manage.model.User;

/**
 * <用户信息接口>
 * 
 * @author 陆小凤
 * @version [版本号, 2014年6月15日]
 * 
 */
@Service
public class SystemUserService
{

	public User findUserByAccount(String username) {
		return null;
	}

	public List<SystemMenu> getMenuByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
