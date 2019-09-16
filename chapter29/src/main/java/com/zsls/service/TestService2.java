package com.zsls.service;

import com.zsls.mapper.test2.Test2Mapper;
import com.zsls.model.Test2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestService2 {
	@Autowired
	private Test2Mapper ts2;


	public void getTeacher() {
		Map<String, Object> info = ts2.getInfo();
		System.out.println(info.toString());

	}

//	public void saveTeacher(Test2 t) {
//		ts2.getsave(t);
//	}

}
