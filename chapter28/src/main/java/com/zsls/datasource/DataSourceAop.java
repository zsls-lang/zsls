package com.zsls.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *@ClassName DataSourceAop
 *@Description
 *@Author zsls
 *@Date 2019/9/11 15:00
 *@Version 1.0
 */
@Aspect
@Component
public class DataSourceAop {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAop.class);

	@Before("execution(* com.zsls.service..*.db1*(..))")
	public void setDataSource2test01() {
		LOGGER.info("test01业务");
		DataSourceContentHolder.setDataSourceType(DataSourceType.DB1);
	}

	@Before("execution(* com.zsls.service..*.db2*(..))")
	public void setDataSource2test02() {
		LOGGER.info("test02业务");
		DataSourceContentHolder.setDataSourceType(DataSourceType.DB2);
	}
}
