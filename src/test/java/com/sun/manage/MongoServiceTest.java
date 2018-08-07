package com.sun.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.WriteResult;
import com.sun.manage.common.util.DateUtil;
import com.sun.manage.entity.mongo.NonDocInterfacesBean;
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
		r.setAge(100);
		r.setD(3.00001);
		r.setName("test");
		r.setDate(new Date());
		/*SysUser us=new SysUser();
		us.setUserId(1001);
		us.setAccount("sammy dong");
		r.setSysUser(us);*/
		NonDocInterfacesBean insert = repos.insert(r);
		System.out.println(insert);
	}
	
	@Test
	public void testAdd2() {
		for(int i=1;i<=100;i++) {
			
			NonDocInterfacesBean r=new NonDocInterfacesBean();
			r.setAge(20);
			r.setD(3.00001);
			r.setName("dongxiu --3");
			r.setDate(new Date());
			SysUser us=new SysUser();
			switch(i%3) {
				case 0:{
					us.setUserId(1001);
					us.setAccount("sammy");
					break;
				}
				case 1:{
					us.setUserId(1002);
					us.setAccount("dong");
					break;
				}
				case 2:{
					us.setUserId(1003);
					us.setAccount("yang");
					break;
				}
			}
			r.setSysUser(us);
			assemberData(i,r);
			NonDocInterfacesBean insert = repos.insert(r);
			System.out.println(insert);
		}
	}
	private void assemberData(int i,NonDocInterfacesBean r) {
		Random random = new Random();
		switch(i%10){
			case 0:{
				r.setAge(10+random.nextInt(10));
				r.setName("dongxiu"+i);
				r.setDate(new Date());
				break;
			}
			case 1:{
				r.setAge(10+random.nextInt(10));
				r.setName("jiangyun"+i);
				r.setDate(new Date());
				break;
			}
			case 2:{
				r.setAge(10+random.nextInt(10));
				r.setName("wanglong"+i);
				r.setDate(new Date());
				break;
			}
			case 3:{
				r.setAge(10+random.nextInt(10));
				r.setName("xiaojie"+i);
				r.setDate(new Date());
				break;
			}
			case 4:{
				r.setAge(10+random.nextInt(10));
				r.setName("yujuan"+i);
				r.setDate(new Date());
				break;
			}
			case 5:{
				r.setAge(10+random.nextInt(10));
				r.setName("chenghao"+i);
				r.setDate(new Date());
				break;
			}
			case 6:{
				r.setAge(10+random.nextInt(10));
				r.setName("wuhan"+i);
				r.setDate(new Date());
				break;
			}
			case 7:{
				r.setAge(10+random.nextInt(10));
				r.setName("shenzhne"+i);
				r.setDate(new Date());
				break;
			}
			case 8:{
				r.setAge(10+random.nextInt(10));
				r.setName("xiaoyao"+i);
				r.setDate(new Date());
				break;
			}
			case 9:{
				r.setAge(10+random.nextInt(10));
				r.setName("liubei"+i);
				r.setDate(new Date());
				break;
			}
			
		}
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
	
	@Test
	public void testGet2() {
		Query query=new Query();
		Criteria main=new Criteria();
		Sort sort = new Sort(Direction.DESC, "age");
		SysUser user=new SysUser();
		user.setUserName("董秀1");
		user.setCity("香港");
		user.setAccount("admin");
		List<SysUser> users=new ArrayList<>();
		users.add(user);
		Criteria criteria1 = Criteria.where("users").is(users);
		Criteria criteria2 = Criteria.where("age").gte(20);
		
		main.andOperator(criteria2).orOperator(criteria1,new Criteria());//orOperator里只有一个元素时相当与and操作
		query.with(sort);
		query.addCriteria(main);
		List<NonDocInterfacesBean> find = mongoTemplate.find(query, NonDocInterfacesBean.class);
		System.out.println(find);
	}
	@Test
	public void testUpdate1() {
		Query query=new Query();
		Criteria main=new Criteria();
		Pattern pattern = Pattern.compile("^.*test.*$", Pattern.CASE_INSENSITIVE);
		Sort sort = new Sort(Direction.ASC, "name");
		Criteria c1 = Criteria.where("name").regex(pattern);
		//Criteria.where("id").exists(true); 使用该方法可判断mongo中该属性是否存在（包括null值）!
		Criteria c2 = Criteria.where("id").exists(true);
		Criteria c3 = Criteria.where("t").exists(true);
		Criteria c4 = Criteria.where("t").is(null);
		Criteria c5 = Criteria.where("t").is("");
		query.with(sort);
		query.addCriteria(main.andOperator(c2,c3).orOperator(c4,c5));
		List<NonDocInterfacesBean> find = mongoTemplate.find(query, NonDocInterfacesBean.class);
		
		Update update=new Update();
		update.inc("d", 1);
		update.set("age", 20);
		WriteResult updateMulti = mongoTemplate.updateMulti(query, update, NonDocInterfacesBean.class);
		boolean updateOfExisting = updateMulti.isUpdateOfExisting();
		
		System.out.println(updateOfExisting);
	}
	@Test
	public void testUpdate2() {
		Query query=new Query();
		Criteria c5 = Criteria.where("id").is("5b69591c67de2f2a68e544e5");
		query.addCriteria(c5);
		Update update=new Update();
		update.set("age", 30);
		update.set("t", null);
		update.set("d", null);
		WriteResult updateMulti = mongoTemplate.updateFirst(query, update, NonDocInterfacesBean.class);
		boolean updateOfExisting = updateMulti.isUpdateOfExisting();
		System.out.println(updateOfExisting);
	}
	@Test
	public void testUpdate3() {
		//当mongodb中某个属性存在时，或者为null,当使用mongoTemplate.save()和repos.save()方法时把该属性置null时，将会删除mongodb该属性。
		//使用上面的testUpdate2方法中Update对象更新属性为null时，mongo中不会删除该属性(如果之前不存在该属性，还会新增null的属性)。
		Query query=new Query();
		Criteria c5 = Criteria.where("id").is("5b69591c67de2f2a68e544e5");
		query.addCriteria(c5);
		NonDocInterfacesBean find = mongoTemplate.findOne(query, NonDocInterfacesBean.class);
		find.setD(22d);
		find.setId(null);
		mongoTemplate.save(find);
		System.out.println(find);
//		NonDocInterfacesBean save = repos.save(find);
	}
	
	/**
	 * mongo 总结容易出错的点：
	 * 1.Criteria.where("d").is(null) 和 Criteria.where("d").is("")查询条件的区别
	 * 			不同点:(1)null和d比较值的不同    (2)is(null):包括不存在该属性的文档   is(""):不包括不存在该属性的文档
	 * 2.Criteria.where("d").ne(null) 和 Criteria.where("d").ne("")
	 * 			不同点:(1)null和d比较值的不同    (2)ne(null):不包括不存在该属性的文档  ne(""):包括不存在该属性的文档
	 * 
	 * 3.当mongodb中某个属性存在时，或者为null,当使用mongoTemplate.save()和repos.save()方法时把该属性置null时，将会删除mongodb该属性。
	 * 4.使用上面的testUpdate2方法中Update对象更新属性为null时，mongo中不会删除该属性(如果之前不存在该属性，还会新增null的属性)。
	 * 
	 */
	
	@Test
	public void testQuery1() {
		//查询d为null的集合数据 is(null):包括不存在该属性的文档   is(""):不包括不存在该属性的文档
		Query query=new Query();
		Criteria main=new Criteria();
//		Criteria c5 = Criteria.where("d").is(null);
		Criteria c5 = Criteria.where("d").is("");
//		Criteria c6 = Criteria.where("d").exists(false);
		
//		main.orOperator(c5);
		query.addCriteria(c5);
		List<NonDocInterfacesBean> find = mongoTemplate.find(query, NonDocInterfacesBean.class);
		System.out.println(find);
	}
	@Test
	public void testQuery2() {
		//查询d存在不为null的集合数据  ne(null):不包括不存在该属性的文档  ne(""):包括不存在该属性的文档
		Query query=new Query();
		Criteria main=new Criteria();
//		Criteria c5 = Criteria.where("d").ne(null);
		Criteria c6 = Criteria.where("d").ne("");
		Criteria c7 = Criteria.where("id").is("5b69632867de2f3b7817aaab");
		
		main.andOperator(c6,c7);
		query.addCriteria(main);
		List<NonDocInterfacesBean> find = mongoTemplate.find(query, NonDocInterfacesBean.class);
		System.out.println(find);
	}
}
