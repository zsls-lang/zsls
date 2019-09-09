package com.zsls.chapter27.service;

import com.zsls.chapter27.mapper.test2.Test2Mapper;
import com.zsls.chapter27.model.Test2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService2 {
	@Autowired
	private Test2Mapper ts2;

	@Transactional
	public void saveTeacher(Test2 t) {
		ts2.save(t);
	}

	@Transactional
	public void saveTeacher2(Test2 t) {
		int i = 1 / 0;
		ts2.save(t);
	}
}
