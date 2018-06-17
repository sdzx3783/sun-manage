package com.sun.manage;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.common.util.PinyinUtil;
import com.sun.manage.entity.sys.SysMenu;
import com.sun.manage.service.sys.SysMenuService;
import com.sun.manage.vo.RoleMenu;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SysMenuServiceTest {
	@Autowired
	private SysMenuService sysMenuService;
	@Test
	public void testSave() {
		SysMenu entity=new SysMenu();
		entity.setName("删除用户");
		entity.setAlias(PinyinUtil.getPinYinHeadCharFilter(entity.getName()));
		entity.setParentId(1);
		entity.setLink("/system/user/del");
		entity.setStatus(0);
		entity.setSort(1);
		sysMenuService.save(entity);
	}
	@Test
	public void getPermission() {
		List<RoleMenu> permissions = sysMenuService.getPermissions("/system/user/list");
		System.out.println(permissions);
	}
}
