package com.zsls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Chapter6Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter6Application.class, args);
	}

}

