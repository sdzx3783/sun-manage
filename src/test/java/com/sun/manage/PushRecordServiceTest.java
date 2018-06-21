package com.sun.manage;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.entity.mongo.PushRecord;
import com.sun.manage.service.mongo.PushRecordService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PushRecordServiceTest {
	@Autowired
	private PushRecordService pushRecordService;
	@Test
	public void testTestQuery() {
		List<PushRecord> testQuery = pushRecordService.testQuery();
		System.out.println("-----"+testQuery.size()+"---------");
	}
	
	@Test
	public void testOneQuery() {
		PushRecord testOneQuery = pushRecordService.testOneQuery();
		System.out.println("-----"+testOneQuery+"---------");
	}
	
	@Test
	public void testPageQuery() {
		Page<PushRecord> testPageQuery = pushRecordService.testPageQuery(1, 10);
		System.out.println("-----"+testPageQuery+"---------");
	}
	
	@Test
	public void testfindByPushResult() {
		List<PushRecord> testQuery = pushRecordService.testfindByPushResult(false);
		System.out.println("-----"+testQuery.size()+"---------");
	}
}
