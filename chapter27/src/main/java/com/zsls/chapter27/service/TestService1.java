package com.zsls.chapter27.service;

import com.zsls.chapter27.mapper.test1.Test1Mapper;
import com.zsls.chapter27.mapper.test2.Test2Mapper;
import com.zsls.chapter27.model.Test1;
import com.zsls.chapter27.model.Test2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService1 {
	@Autowired
	private Test1Mapper test1Mapper;
	@Autowired
	private TestService2 ts2;
	@Autowired
	private Test2Mapper td2;

	@Transactional
	public void savetestBean(Test1 t) {
		test1Mapper.save(t);
	}

	@Transactional
	public void savetestBean2(Test1 t) {
		Test2 tb = new Test2();
		tb.setName("王老师");
		ts2.saveTeacher(tb);
		int i = 1 / 0;
		test1Mapper.save(t);
	}

	@Transactional
	public void savetestBean3(Test1 t) {
		Test2 tb = new Test2();
		tb.setName("李老师");
		ts2.saveTeacher2(tb);
		int i = 1 / 0;
		test1Mapper.save(t);
	}

	@Transactional
	/**
	 * 直接注入数据源2的dao层就不收这个事务控制了
	 * 
	 * @param t
	 */
	public void savetestBean4(Test1 t) {
		Test2 tb = new Test2();
		tb.setName("王老师");
		td2.save(tb);
		int i = 1 / 0;
		test1Mapper.save(t);
	}

}
