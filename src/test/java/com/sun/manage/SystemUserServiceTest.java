package com.sun.manage;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.entity.sys.SysRole;
import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.service.sys.SysRoleService;
import com.sun.manage.service.sys.SysUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SystemUserServiceTest {

	@Autowired
	private SysUserService systemUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Test
	public void testSave() {
		SysUser user=new SysUser();
		user.setAccount("admin");
		user.setUserName("sun"+new Random().nextInt(1000));
		user.setPassword("123456");
		systemUserService.entryptPassword(user);
		user.setBirthday(new Date());
		user.setCtime(new Date());
		user.setMobile("17607173457");
		systemUserService.save(user);
	}
	@Test
	public void testSaveUserRole() {
		for (int i=0;i<=100;i++) {
			SysUser user=new SysUser();
			user.setAccount("sammy"+i);
			user.setUserName("测试账号"+new Random().nextInt(1000));
			user.setPassword("123456");
			systemUserService.entryptPassword(user);
			user.setBirthday(new Date());
			user.setCtime(new Date());
			user.setMobile("17607173457");
			Map<String,Object> searchParams=new HashMap<>();
			//searchParams.put("IN_roleId", "1,2,3");
			searchParams.put("IN_roleId", i%6);
			List<SysRole> findAll = sysRoleService.findAll(searchParams);
			if(findAll!=null) {
				user.setRoles(new HashSet<>(findAll));
			}
			systemUserService.save(user);
		}
	}

	@Test
	public void testDeleteRole() {
		SysUser byAccount = systemUserService.findOne(105);
		if(byAccount!=null) {
			byAccount.setRoles(null);
		}
		systemUserService.save(byAccount);
	}
	@Test
	public void testFindAll() {
		Map<String, Object> searchParams = new HashMap();
        searchParams.put("LIKE_userName","测试账号");
//        searchParams.put("LIKE_account","admin");
        Page<SysUser> findAll = systemUserService.findAll(searchParams,2,10);
        long totalElements = findAll.getTotalElements();
        int totalPages = findAll.getTotalPages();
        System.out.println(ReflectionToStringBuilder.toString(findAll));
	}
	

	@Test
	public void testDeleteListOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOneID() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOneMapOfStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testExists() {
		fail("Not yet implemented");
	}

	@Test
	public void testCount() {
		fail("Not yet implemented");
	}


	@Test
	public void testFindAllListOfID() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectIntIntDirectionString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectIntIntDirectionStringArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectIntIntDirectionListOfString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectIntIntListOfDirectionListOfString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectPageRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllMapOfStringObjectSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchUpdate() {
		fail("Not yet implemented");
	}

}
