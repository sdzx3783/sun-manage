package com.sun.manage.service.sys;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.manage.entity.sys.SysRole;
import com.sun.manage.repository.sys.SysRoleRepository;
import com.sun.manage.service.BaseService;
@Service
public class SysRoleService extends BaseService<SysRole, Integer>{
	private SysRoleRepository sysRoleRepository;
	
	@Transactional
	@Override
	public void delete(Integer id) {
		sysRoleRepository.delete(id);
	}
	
	
}
