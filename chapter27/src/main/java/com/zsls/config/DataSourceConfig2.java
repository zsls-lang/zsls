package com.zsls.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zsls.property.DB2Property;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zsls.mapper.test2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class DataSourceConfig2 {

	@Value("${db2.mapper}")
	private String db2Mapper;
	@Value("${db2.aliases.package}")
	private String aliasesPackage;

	@Autowired
	private DB2Property db;

	//	@Bean(name = "db2")
	//	@ConfigurationProperties(prefix = "spring.datasource.db2")
	//	public DataSource dataSource() {
	//		return DataSourceBuilder.create().build();
	//	}

	@Bean(name = "db2")
	public DataSource dataSource() throws Exception {
		MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setURL(db.getJdbcUrl());
		mysqlXADataSource.setPassword(db.getPassword());
		mysqlXADataSource.setUser(db.getUsername());
		mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXADataSource);
		xaDataSource.setUniqueResourceName("db2");
		return xaDataSource;
	}


	@Bean(name = "db2SqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("db2") DataSource dataSource) throws Exception {
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
		String resouces[] = db2Mapper.split(",");
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
