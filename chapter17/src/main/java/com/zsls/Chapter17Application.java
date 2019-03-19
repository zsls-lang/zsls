package com.zsls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * @Description //TODO
 * 可以参考tomcat 里面的 apache-tomcat-8.5.33\webapps\examples\websocket 的html
 * apache-tomcat-8.5.33\webapps\examples\WEB-INF\classes\websocket 的java
 */
@EnableWebSocket
@SpringBootApplication
public class Chapter17Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter17Application.class, args);
	}

	/**
	 * @Description //
	 * 首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
