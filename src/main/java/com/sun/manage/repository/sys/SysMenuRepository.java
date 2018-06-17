package com.sun.manage.repository.sys;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.sun.manage.entity.sys.SysMenu;
import com.sun.manage.repository.BaseRepository;

public interface SysMenuRepository extends BaseRepository<SysMenu, Integer>
{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from SysMenu s where s.id=?1")
	SysMenu lockSysMenu(Integer id);
	@Query(nativeQuery=true,value="SELECT\r\n" + 
			" sm.alias as menu,sr.alias as role \r\n" + 
			"FROM\r\n" + 
			"	sys_menu sm\r\n" + 
			"LEFT JOIN sys_role_menu_rel rel ON sm.id = rel.menu_id\r\n" + 
			"LEFT JOIN sys_role sr ON rel.role_id = sr.id AND sr.`status`=0\r\n" + 
			"WHERE sm.link=?1")
	List<Object[]> getPermissions(String link);
}
