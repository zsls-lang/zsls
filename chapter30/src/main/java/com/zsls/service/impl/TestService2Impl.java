package com.zsls.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsls.mapper.db1.Test1Mapper;
import com.zsls.mapper.db2.Test2Mapper;
import com.zsls.model.Test2;
import com.zsls.service.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestService2Impl implements TestService2 {
	@Autowired
	private Test2Mapper test2Mapper;

	@Autowired
	private Test1Mapper test1Mapper;


	@Override
	public void getTeacher() {
		PageInfo<Map<String, Object>> pageInfo = PageHelper.startPage(1, 2).doSelectPageInfo(() -> test2Mapper.getInfo());
		System.out.println(pageInfo.toString());

	}

	@Override
	public void saveTeacher(Test2 t) {
		test2Mapper.getsave(t);
	}

	@Override
	public void saveTeacherYYY(Test2 t) {
		test2Mapper.getsave(t);
		double a = 1 / 0;
		test1Mapper.getInfo();
	}

}