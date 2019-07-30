package com.zsls;

import com.zsls.service.UserService;
import com.zsls.service.UserService2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter26ApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(Chapter26ApplicationTests.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserService2 userService2;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sendMessage() throws InterruptedException {
		Long beginTime = System.currentTimeMillis();
//		userService.doLogin("zk1023@163.com", "12345678911");//完成注册请求
		userService2.doLogin("zk1023@163.com", "12345678911");//完成注册请求
		LOGGER.info("处理注册相关业务耗时{}ms",(System.currentTimeMillis() - beginTime));
		LOGGER.info("处理其他业务逻辑");
		Thread.sleep(500);//模拟处理其他业务请求耗时
		LOGGER.info("处理所有业务耗时{}ms",(System.currentTimeMillis() - beginTime));
		LOGGER.info("向客户端发送注册成功响应");

	}

}
