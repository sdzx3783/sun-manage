package com.sun.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.entity.mongo.NonDocInterfacesBean;
import com.sun.manage.entity.mongo.PushRecord;
import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.service.mongo.PushRecordService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PushRecordServiceTest {
	@Autowired
	private PushRecordService pushRecordService;
	
	
	@Test
	public void testInsert() {
		PushRecord pushRecord=new PushRecord();
		pushRecord.setContent("哈哈哈。。。abc");
		pushRecord.setDevId("0001.0002");
		pushRecord.setMessageId(1111);
		PushRecord testAddPushRecord = pushRecordService.testAddPushRecord(pushRecord);
		System.out.println("-----"+testAddPushRecord+"---------");
	}
	/**
	 * ObjectId 是"_id" 的默认类型。ObjectId 使用12 字节的存储空间，每个字节两位十六进制数字，是一个24 位的字符串
	 * 
	 * id赋值时，如果不是24位，那么就不会被当成ObjectId类型而是String类型保存到DB
	 */
	@Test
	public void testSave() {
		PushRecord pushRecord=new PushRecord();
//		pushRecord.setId("5b2f2f67cd76da26d0ddea66");
		pushRecord.setId("5b2f2f67cd76da26d0ddea6");
		pushRecord.setContent("哈哈哈 修改了。。。abc");
		pushRecord.setDevId("0001.0002");
		pushRecord.setMessageId(2222);
		PushRecord testAddPushRecord = pushRecordService.testSavePushRecord(pushRecord);
		System.out.println("-----"+testAddPushRecord+"---------");
	}
	
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
//	=============测试MongoTemplate===============
//	===================insert方法有一个缺陷，就是每调用一次，
//			就会插入一条新的数据，但是有很多时候，我们需要如果数据存在，则修改，如果不存在，则插入，这个时候，更新操作是比较常用的。==========================
	@Test
	public void testMongoTemplateQuery() {
		PushRecord testMonoTemplateFindOne = pushRecordService.testMonoTemplateFindOne();
		System.out.println("-----"+testMonoTemplateFindOne+"---------");
	}
	@Test
	public void testMongoTemplateQuery1() {
		NonDocInterfacesBean testMonoTemplateFindOne1 = pushRecordService.testMonoTemplateFindOne1();
		System.out.println("-----"+testMonoTemplateFindOne1+"---------");
	}
	
	@Test
	public void testAddNonDocInterfacesBeanByMongoTemplate() {
		NonDocInterfacesBean testMonoTemplateFindOne1 = new NonDocInterfacesBean();
		testMonoTemplateFindOne1.setAge(22);
		testMonoTemplateFindOne1.setD(10d);
		testMonoTemplateFindOne1.setDate(new Date());
		List<SysUser> users=new ArrayList<>();
		SysUser sysUser = new SysUser();
		sysUser.setAccount("admin");
		sysUser.setCity("香港");
		sysUser.setUserName("孙朝");
		users.add(sysUser);
		testMonoTemplateFindOne1.setUsers(users);
		pushRecordService.testAddNonDocInterfacesBeanByMongoTemplate(testMonoTemplateFindOne1);
		System.out.println("-----"+testMonoTemplateFindOne1+"---------");
	}
}
