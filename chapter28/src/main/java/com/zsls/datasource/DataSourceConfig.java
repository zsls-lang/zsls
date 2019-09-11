package com.zsls.datasource;

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

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName DataSourceConfig
 *@Description
 *@Author zsls
 *@Date 2019/9/11 15:59
 *@Version 1.0
 */
@Configuration
@MapperScan(basePackages = "com.zsls.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

	@Value("${db.mapper}")
	private String dbMapper;
	@Value("${db.aliases.package}")
	private String aliasesPackage;

	@Primary
	@Bean(name = "db1")
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	public DataSource dataSourceDB1() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "db2")
	@ConfigurationProperties(prefix = "spring.datasource.db2")
	public DataSource dataSourceDB2() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dynamicDataSource")
	public DynamicDataSource DataSource() {
		Map<Object, Object> targetDataSource = new HashMap<>();
		targetDataSource.put(DataSourceType.DB1, dataSourceDB1());
		targetDataSource.put(DataSourceType.DB2, dataSourceDB2());
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSource);
		dataSource.setDefaultTargetDataSource(dataSourceDB1());
		return dataSource;
	}


	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
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
		String resouces[] = dbMapper.split(",");
		Resource[] resourceArray = null;
		for (String resouce : resouces) {
			Resource[] resourceArray1 = resolver.getResources(resouce);
			if (resourceArray == null) {
				resourceArray = resourceArray1;
			} else {
				resourceArray = ArrayUtils.addAll(resourceArray, resourceArray1);
			}
		}
		factoryBean.setMapperLocations(resourceArray);
		return factoryBean.getObject();
	}
}
