package com.zsls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Chapter26Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter26Application.class, args);
	}



}
