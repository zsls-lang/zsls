package com.zsls;

import com.zsls.endpoint.MyEndPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Chapter7Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter7Application.class, args);
	}
	@Configuration
	static class MyEndpointConfiguration {
		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnEnabledEndpoint
		public MyEndPoint myEndPoint() {
			return new MyEndPoint();
		}
	}
}

