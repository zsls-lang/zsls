package com.zsls.chapter27.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zsls.chapter27.mapper.test1", sqlSessionFactoryRef = "test1dbSqlSessionFactory")
public class DataSourceConfig1 {

	@Value("${db1.mapper}")
	private String db1Mapper;
	@Value("${db1.aliases.package}")
	private String aliasesPackage;

	@Primary
	@Bean(name = "db1")
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "test1dbTransactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("db1") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Primary
	@Bean(name = "test1dbSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("db1") DataSource dataSource) throws Exception {
		VFS.addImplClass(SpringBootVFS.class);
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setTypeAliasesPackage(aliasesPackage);

		// Mybatis Config
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		factoryBean.setConfiguration(configuration);

		// Mybatis Mapper XML Config
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		//*.mapper.xml的地址（根据你的项目自行修改）
		String resouces[] = db1Mapper.split(",");
		Resource[] resourceArray = null;
		for(String resouce : resouces){
			Resource[] resourceArray1 = resolver.getResources(resouce);
			if(resourceArray==null){
				resourceArray = resourceArray1;
			}
			else {
				resourceArray = ArrayUtils.addAll(resourceArray, resourceArray1);
			}
		}
		factoryBean.setMapperLocations(resourceArray);
		return factoryBean.getObject();
	}

}
