package com.zsls.datasource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
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
import org.springframework.transaction.PlatformTransactionManager;

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
	@Bean(name = "master")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource master() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "slave1")
	@ConfigurationProperties(prefix = "spring.datasource.slave1")
	public DataSource slave1() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dynamicDataSource")
	public DynamicDataSource dataSource() {

		DynamicDataSource dynamicRoutingDataSource = new DynamicDataSource();
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(DataSourceKey.MASTER.name(), master());
		dataSourceMap.put(DataSourceKey.SLAVE1.name(), slave1());

		// Set master datasource as default
		dynamicRoutingDataSource.setDefaultTargetDataSource(master());
		// Set master and slave datasource as target datasource
		dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

		// To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
		DataSourceContentHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

		// To put slave datasource keys into DataSourceContextHolder to load balance
		DataSourceContentHolder.slaveDataSourceKeys.addAll(dataSourceMap.keySet());
		DataSourceContentHolder.slaveDataSourceKeys.remove(DataSourceKey.MASTER.name());
		return dynamicRoutingDataSource;
	}


	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		VFS.addImplClass(SpringBootVFS.class);
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
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

	/**
	 * Transaction manager platform transaction manager.
	 *
	 * @return the platform transaction manager
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
