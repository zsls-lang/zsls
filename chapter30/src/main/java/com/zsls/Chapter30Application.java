package com.zsls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//@ComponentScan(basePackages = {
//		"com.zsls"
//},
//		useDefaultFilters = false,
//		includeFilters = {
//				@ComponentScan.Filter(classes = Configuration.class),
//				@ComponentScan.Filter(classes = Repository.class),
//				@ComponentScan.Filter(classes = Service.class),
//				@ComponentScan.Filter(classes = Component.class),
//				@ComponentScan.Filter(classes = RestController.class)
//		})
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

