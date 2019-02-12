package com.zsls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Chapter9Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter9Application.class, args);
	}

	@Bean
	public TaskScheduler threadPoolExecutor(){
		ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
		taskExecutor.setPoolSize(10);
		return taskExecutor;
	}

}

