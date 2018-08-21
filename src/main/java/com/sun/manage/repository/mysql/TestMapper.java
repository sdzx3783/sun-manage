package com.sun.manage.repository.mysql;


import java.util.List;

import com.sun.manage.entity.Test;

import tk.mybatis.mapper.common.Mapper;

public interface TestMapper extends Mapper<Test> {

	/**
	 * @param tests
	 * @return
	 */
	int insertBatch(List<Test> tests);
}