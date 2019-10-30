package com.zsls.service.impl;

import com.zsls.mapper.db1.Test1Mapper;
import com.zsls.model.Test1;
import com.zsls.service.TestService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService1Impl implements TestService1 {
	@Autowired
	private Test1Mapper test1Mapper;

	@Override
	public void savetestBean(Test1 t) {
		test1Mapper.save(t);
	}


}
