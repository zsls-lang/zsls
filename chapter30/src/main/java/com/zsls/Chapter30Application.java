package com.zsls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class Chapter30Application extends WebMvcConfigurationSupport implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(Chapter30Application.class);
	@Override
	public void run(String... args) throws Exception {
		logger.info("服务启动完成!");
	}
	public static void main(String[] args) {
		SpringApplication.run(Chapter30Application.class, args);
	}
}

