package com.zsls;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 *@RestController 等同于 （@Controller 与 @ResponseBody）
 */
@RestController
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx){
		return args -> {
			System.out.println("SpringBoot 默认提供的 Bean");
			String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
			Arrays.stream(beanDefinitionNames).forEach(item-> System.out.println(item));
		};
	}

}

