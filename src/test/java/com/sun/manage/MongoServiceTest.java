package com.sun.manage;

import java.util.Date;
import java.util.List;

import org.hibernate.loader.criteria.CriteriaLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.manage.common.util.DateUtil;
import com.sun.manage.entity.mongo.NonDocInterfacesBean;
import com.sun.manage.entity.mongo.PushRecord;
import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.repository.mongo.NonDocInterfacesBeanRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoServiceTest {
	@Autowired
	private NonDocInterfacesBeanRepository repos;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Test
	public void testAdd() {
		NonDocInterfacesBean r=new NonDocInterfacesBean();
		r.setAge(20);
		r.setD(3.00001);
		r.setName("dongxiu --3");
		r.setDate(new Date());
		SysUser us=new SysUser();
		us.setUserId(1001);
		us.setAccount("sammy dong");
		r.setSysUser(us);
		NonDocInterfacesBean insert = repos.insert(r);
		System.out.println(insert);
	}
	@Test
	public void testGet() {
		Query query=new Query();
		Criteria main=new Criteria();
		Date begin = DateUtil.parseDate("2016-07-25 23:32:33.767");
//		Date end = DateUtil.parseDate("2018-07-29 18:59:07.312");
		Date end = new Date();
		Criteria gt = Criteria.where("date").gte(begin).lte(end);
		SysUser user=new SysUser();
		user.setUserId(1000);
		user.setAccount("sammy xiu");
		Criteria or1 = Criteria.where("sysUser").is(user);
		SysUser user1=new SysUser();
		user1.setUserId(1001);
		user1.setAccount("sammy dong");
		Criteria or2 = Criteria.where("sysUser").is(user1);
		Criteria or=new Criteria();
		or.orOperator(or1,or2);
		main.andOperator(gt,or);
		query.addCriteria(main);
		List<NonDocInterfacesBean> find = mongoTemplate.find(query, NonDocInterfacesBean.class);
		System.out.println(find);
		List<NonDocInterfacesBean> findAll = mongoTemplate.findAll(NonDocInterfacesBean.class);
		System.out.println(findAll);
	}
}
