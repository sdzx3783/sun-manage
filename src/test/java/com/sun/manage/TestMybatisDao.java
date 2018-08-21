package com.sun.manage;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.service.TestService;

/**  
 * @Title:  TestMybatisDao.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月9日 上午10:47:13   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMybatisDao {
	@Autowired
	private TestService testService;
	
	//当开启mybatis缓存时,使用事物时一级缓存会出现
	@Test
	public void testCache() {
		testService.testCache();
	}
	@Test
	public void insertBatch() {
		List<com.sun.manage.entity.Test> tests=new ArrayList<>();
		for(int i=0;i<=5;i++) {
			com.sun.manage.entity.Test t=new com.sun.manage.entity.Test();
			t.setStatus(i);
			t.setName("name:"+i);
			tests.add(t);
		}
		int insertBatch = testService.insertBatch(tests);
		System.out.println(insertBatch);
	}
}
