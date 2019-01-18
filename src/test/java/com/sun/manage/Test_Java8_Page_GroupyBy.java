package com.sun.manage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**  
 * @Title:  Test2.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月28日 下午3:02:04   
 */
public class Test_Java8_Page_GroupyBy {
	private static class Person{
		private String id;
		private Integer age;
		private Date birthday;
		
		
		/**
		 * @param id
		 * @param age
		 */
		public Person(String id, Integer age) {
			super();
			this.id = id;
			this.age = age;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		@Override
		public String toString() {
			return "Person [id=" + id + ", age=" + age + ", birthday=" + birthday + "]";
		}
		
		
	}
	//并行是指两个或者多个事件在同一时刻发生；而并发是指两个或多个事件在同一时间间隔发生
	public static void main(String[] args) {
		/*List<Person> list = Arrays.asList(new Person("001", 11),new Person("002", 22),new Person("003", 05));
		List<Integer> collect = list.parallelStream().map((s)->{
			System.out.println(s);
			return s.getAge();
		}).collect(Collectors.toList());
		System.out.println(collect);*/
		Map<String, Object> params=new HashMap<>();
		params.put("age", 15);
		params.put("id", "0000000015");
		testPage(2, 10, params);
	}
	
	/**
	 * 测试分页搜索
	 * @param page
	 * @param pageSize
	 * @param params
	 */
	public static void testPage(int page,int pageSize,Map<String,Object> params) {
		int i=0;
		List<Person> ps=new ArrayList<>();
		while(i<=1000) {
			ps.add(new Person(IdGenerator.getId(10), new Random().nextInt(15)+10));
			i++;
		}
		
		Optional<Person> findFirst = ps.stream().findFirst();
		System.out.println(findFirst.get());
		IntSummaryStatistics collect2 = ps.parallelStream().filter((p)->p.getAge().compareTo((Integer) params.get("age"))==0).collect(Collectors.summarizingInt((p1)->1));
		System.out.println(collect2);
		int totalSize = ps.parallelStream().filter((p)->p.getAge().compareTo((Integer) params.get("age"))==0).mapToInt(p1->1).sum();
		System.out.println("总记录数："+totalSize+" 总记录数："+((totalSize%pageSize==0)?totalSize/pageSize:totalSize/pageSize+1));
		List<Person> collect = ps.parallelStream().filter((p)->p.getAge().compareTo((Integer) params.get("age"))==0).skip((page-1)*pageSize).limit(pageSize).collect(Collectors.toList());
		System.out.println(collect);
		
		
		//分组聚合
		Map<Integer, List<Person>> collect3 = ps.parallelStream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.groupingBy(Person::getAge));
		collect3.forEach((k,v)->System.out.println(v));
		
	}
}
