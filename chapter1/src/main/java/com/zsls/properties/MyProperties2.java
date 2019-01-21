package com.zsls.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:my2.properties")
@ConfigurationProperties(prefix="my2")
public class MyProperties2 {
	private String name;
	private String age;
	private String email;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MyProperties2{" +
				"age=" + age +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
