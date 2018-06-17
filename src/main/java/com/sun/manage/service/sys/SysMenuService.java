package com.sun.manage.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.manage.entity.sys.SysMenu;
import com.sun.manage.repository.sys.SysMenuRepository;
import com.sun.manage.service.BaseService;
import com.sun.manage.vo.RoleMenu;

@Service
public class SysMenuService extends BaseService<SysMenu, Integer>
{
	@Autowired
	private SysMenuRepository sysMenuRepository;
	@Transactional
	@Override
	public SysMenu save(SysMenu entity) {
		SysMenu sm = super.save(entity);
		Integer parentId = entity.getParentId();
		if(parentId==null || parentId==0) {
			sm.setPath("0."+sm.getId()+".");
		}else {
			SysMenu lockSysMenu = sysMenuRepository.lockSysMenu(parentId);
			if(lockSysMenu!=null) {
				sm.setPath(lockSysMenu.getPath()+sm.getId()+".");
			}
		}
		return sm;
	}
	
	
	public List<RoleMenu> getPermissions(String link){
		List<Object[]> permissions = sysMenuRepository.getPermissions(link);
		List<RoleMenu> rms=new ArrayList<>();
		if(permissions!=null && permissions.size()>0) {
			for (Object[] ps : permissions) {
				rms.add(new RoleMenu((String)ps[0], (String)ps[1]));
			}
		}
		return rms;
	}
}
