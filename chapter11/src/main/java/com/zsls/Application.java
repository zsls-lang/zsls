package com.zsls;

import com.github.zsls.base.BaseMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *@ClassName Application
 *@Description TODO
 *@Author zsls
 *@Date 2019/3/27 9:52
 *@Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zsls.dao")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
