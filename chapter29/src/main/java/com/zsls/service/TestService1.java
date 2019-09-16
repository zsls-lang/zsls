package com.zsls.service;

import com.zsls.mapper.test1.Test1Mapper;
import com.zsls.model.Test1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService1 {
	@Autowired
	private Test1Mapper test1Mapper;

	public void savetestBean(Test1 t) {
		test1Mapper.save(t);
	}


}
