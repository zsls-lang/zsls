package com.zsls.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 *@ClassName DruidConfig
 *@Description 本项目 http://localhost:8088/multiple/druid/login.html
 *@Date 2019/11/6 21:14
 *@Version 1.0
 */
@SpringBootConfiguration
public class DruidConfig {

	@Bean
	public ServletRegistrationBean druidStatViewServlet() {
		//注册服务
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		// 白名单（为空表示，所有的都可以访问，多个IP的时候用逗号隔开）
		registrationBean.addInitParameter("allow", "127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow)
		// registrationBean.addInitParameter("deny", "127.0.0.2");
		registrationBean.addInitParameter("deny", "");
		//设置登录的用户名和密码
		registrationBean.addInitParameter("loginUsername", "zsls");
		registrationBean.addInitParameter("loginPassword", "zsls");
		//是否能够重置数据
		registrationBean.addInitParameter("resetEnable", "false");
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean druidWebStatViewFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则
		registrationBean.addInitParameter("urlPatterns", "/*");
		// 添加不需要忽略的格式信息
		registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		return registrationBean;
	}
}
