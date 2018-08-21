package com.sun.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.manage.entity.Test;
import com.sun.manage.repository.mysql.TestMapper;

/**  
 * @Title:  TestService.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月9日 上午11:02:40   
 */
@Service
public class TestService {
	@Autowired
	private TestMapper testDao;
	@Transactional
	public void testCache() {
		com.sun.manage.entity.Test test = testDao.selectByPrimaryKey(1);
		System.out.println(test);
		com.sun.manage.entity.Test test1 = testDao.selectByPrimaryKey(1);
		System.out.println(test1);
	}
	
	public int insertBatch(List<Test> tests) {
		return testDao.insertBatch(tests);
	}
}
