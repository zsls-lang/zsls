package com.zsls;

import com.zsls.interceptor.CacheKeyGenerator;
import com.zsls.interceptor.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Chapter15Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter15Application.class, args);
	}
	@Bean
	public CacheKeyGenerator cacheKeyGenerator() {
		return new LockKeyGenerator();
	}
}
