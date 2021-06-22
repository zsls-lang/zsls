package com.zsls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude =org.activiti.spring.boot.ActivitiMethodSecurityAutoConfiguration.class)
@SpringBootApplication
public class Chapter37Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter37Application.class, args);
    }

}
