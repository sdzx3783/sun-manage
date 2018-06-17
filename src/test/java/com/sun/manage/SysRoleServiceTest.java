package com.sun.manage;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.common.util.PinyinUtil;
import com.sun.manage.entity.sys.SysMenu;
import com.sun.manage.entity.sys.SysRole;
import com.sun.manage.service.sys.SysMenuService;
import com.sun.manage.service.sys.SysRoleService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SysRoleServiceTest {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;
	@Test
	public void test() {
		SysRole r=new SysRole();
		r.setRoleName("文档管理员");
		r.setAlias(PinyinUtil.getPinYinHeadCharFilter(r.getRoleName()));
		SysMenu findOne = sysMenuService.findOne(1);
		Set<SysMenu> menuSet=new HashSet<>();
		menuSet.add(findOne);
		r.setMenuSet(menuSet);
		sysRoleService.save(r);
	}

}
