package com.zsls.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *@ClassName SpringTaskDemo
 *@Description 基于 Spring 自带的
 *
 *@Author zsls
 *@Date 2019/2/12 16:56
 *@Version 1.0
 */
@Component
public class SpringTaskDemo {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringTaskDemo.class);

	@Async
	@Scheduled(cron = "0/1 * * * * *")
	public void schedule1() throws InterruptedException {
		Thread.sleep(3000);
		LOGGER.info("schedule1 {} ", LocalDateTime.now());
	}

	@Scheduled(fixedRate=1000)
	public void schedule2() throws InterruptedException {
		Thread.sleep(3000);
		LOGGER.info("schedule2 {} ", LocalDateTime.now());
	}

	@Scheduled(fixedDelay = 3000)
	public void scheduled3() throws InterruptedException {
		Thread.sleep(5000);
		LOGGER.info("scheduled3 上次执行完毕后隔3秒继续执行：{}", LocalDateTime.now());
	}



}
