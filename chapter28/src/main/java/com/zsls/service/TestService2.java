package com.zsls.service;

import com.zsls.mapper.test2.Test2Mapper;
import com.zsls.model.Test2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService2 {
	@Autowired
	private Test2Mapper ts2;

	@Transactional
	public void db2_saveTeacher(Test2 t) {
		ts2.save(t);
	}

}
