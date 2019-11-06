package com.zsls.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zsls.property.DataSourceProperty;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * DB2据源配置类
 */
@SpringBootConfiguration
@MapperScan(basePackages = "com.zsls.mapper.db2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class DB2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(DB2.class);

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext ctx;

	@Autowired
	private DataSourceProperty dataSourceProperty;

    @Bean(name="db2DataSource")
	@Primary
    public DataSource db2DataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        // 基本属性
        dataSource.setDriverClassName(env.getProperty("db2.driver-class-name"));
        dataSource.setUrl(env.getProperty("db2.jdbc-url"));
        dataSource.setUsername(env.getProperty("db2.username"));
        dataSource.setPassword(env.getProperty("db2.password"));

		// 配置初始化大小、最小、最大
		dataSource.setInitialSize(dataSourceProperty.getInitialSize());
		dataSource.setMinIdle(dataSourceProperty.getMinIdle());
		dataSource.setMaxActive(dataSourceProperty.getMaxActive());

		// 配置获取连接等待超时的时间，单位是毫秒
		dataSource.setMaxWait(dataSourceProperty.getMaxWait());

		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(dataSourceProperty.getTimeBetweenEvictionRunsMillis());

		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(dataSourceProperty.getMinEvictableIdleTimeMillis());

		// 配置连接存活测试
		dataSource.setValidationQuery(dataSourceProperty.getValidationQuery());
		dataSource.setTestWhileIdle(dataSourceProperty.getTestWhileIdle());
		dataSource.setTestOnBorrow(dataSourceProperty.getTestOnBorrow());
		dataSource.setTestOnReturn(dataSourceProperty.getTestOnReturn());
		dataSource.setPoolPreparedStatements(dataSourceProperty.getPoolPreparedStatements());
		try {
			dataSource.setFilters(dataSourceProperty.getFilters());
		} catch (SQLException e) {
			LOGGER.error("dataSourceProperty.getFilters==>",e);
		}

		return dataSource;
    }

    @Bean(name="db2SqlSessionFactory")
	@Primary
    public SqlSessionFactory db2SqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(db2DataSource());

        // Mybatis Config
        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        // Mybatis Mapper XML Config
        Resource[] resourceArray = ctx.getResources("classpath:/mappers/db2/*.xml");
        sessionFactory.setMapperLocations(resourceArray);

        return sessionFactory.getObject();
    }
    
    @Bean(name="db2TransactionManager")
	@Primary
	public PlatformTransactionManager db2TransactionManager() {
	    return new DataSourceTransactionManager(db2DataSource());
	}
}
