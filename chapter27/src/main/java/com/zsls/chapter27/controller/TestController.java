package com.zsls.chapter27.controller;

import com.zsls.chapter27.model.Test1;
import com.zsls.chapter27.model.Test2;
import com.zsls.chapter27.service.TestService1;
import com.zsls.chapter27.service.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多数据源事务测试
 * 
 * @author acer
 *
 */
@RestController
public class TestController {
	@Autowired
	private TestService1 ts1;
	@Autowired
	private TestService2 ts2;

	@RequestMapping("/savetest1")
	public String savetest() {
		Test1 tb = new Test1();
		tb.setName("xxx");
		ts1.savetestBean(tb);
		return "success";
	}

	@RequestMapping("/savetest2")
	public String saveteacher() {
		Test2 tb = new Test2();
		tb.setName("王老师");
		ts2.saveTeacher(tb);
		return "success";
	}

	// ########################开始事务测试##########################
	/**
	 * 结果是一个插入进去了，属于非正常现象
	 * 
	 * @return
	 */
	@RequestMapping("/test.do")
	public String test() {
		Test1 tb = new Test1();
		tb.setName("xoxox");
		ts1.savetestBean2(tb);
		return "success";
	}

	/**
	 * 结果是两个都没法插入---属于正常现象
	 * 
	 * @return
	 */
	@RequestMapping("/test2.do")
	public String test2() {
		Test1 tb = new Test1();
		tb.setName("aaaaa");
		ts1.savetestBean3(tb);
		return "success";
	}

	/**
	 * 结果是一个插入进去了，属于非正常现象
	 * 
	 * @return
	 */
	@RequestMapping("/test3.do")
	public String test3() {
		Test1 tb = new Test1();
		tb.setName("bbb");
		ts1.savetestBean4(tb);
		return "success";
	}

	@RequestMapping("/test4.do")
	public String test4() {
		Test1 tb = new Test1();
		tb.setName("bbb");
		ts1.savetestBean5(tb);
		return "success";
	}
}
