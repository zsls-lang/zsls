package com.zsls.chapter27.config;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 *@ClassName DataSourceConfig1
 *@Description TODO
 *@Author zsls
 *@Date 2019/9/6 11:49
 *@Version 1.0
 */
@Configuration
@MapperScan(basePackages = "com.zsls.*.mapper.test2", sqlSessionFactoryRef = "test2SqlSessionFactory")
public class DataSourceConfig2 {
	// 将这个对象放入Spring容器中
	@Bean(name = "test2DataSource")
	// 读取application.properties中的配置参数映射成为一个对象
	// prefix表示参数的前缀
	@ConfigurationProperties(prefix = "spring.datasource.test2")
	public DataSource test2DataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean("test2SqlSessionFactory")
	// @Qualifier表示查找Spring容器中名字为test2DataSource的对象
	public SqlSessionFactory test2SqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource)
			throws Exception {
		//解决myBatis下 不能嵌套jar文件的问题
		//工程上默认使用的是Mybatis的DefaultVFS进行扫描，但是在springboot的环境下，Mybatis的DefaultVFS这个扫包会出现问题，所以只能修改VFS
		VFS.addImplClass(SpringBootVFS.class);
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(
				// 设置mybatis的xml所在位置
				new PathMatchingResourcePatternResolver().getResource("classpath*:/mapper/test2/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean("test2SqlSessionTemplate")
	public SqlSessionTemplate test2SqlSessionTemplate(
			@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
