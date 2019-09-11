package com.zsls.service;

import com.zsls.mapper.test1.Test1Mapper;
import com.zsls.mapper.test2.Test2Mapper;
import com.zsls.model.Test1;
import com.zsls.model.Test2;
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
	private Test2Mapper test2Mapper;

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


	/**
	 * 直接注入数据源2的dao层就不收这个事务控制了
	 * 
	 * @param t
	 */
	@Transactional
	public void savetestBean4(Test1 t) {
		Test2 tb = new Test2();
		tb.setName("王老师");
		test2Mapper.save(tb);
		int i = 1 / 0;
		test1Mapper.save(t);
	}

	/**
	 * @Description
	 * 断点查看，当执行完第一个库的插入时，刷新数据库表是没有数据的，
	 * 这表明这连个事务处于同一个事务中，执行到报错一行后，
	 * 查看数据库，没有插入数据，这表明整合分布式事务成功
	 */
	@Transactional
	public void savetestBean5(Test1 t) {
		Test2 tb = new Test2();
		tb.setName("李老师");
//		test2Mapper.save(tb);
//		test1Mapper.save(t);
		ts2.saveTeacher(tb);
		savetestBean(t);
		int i = 1 / 0;


	}

}
