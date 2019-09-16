package com.zsls.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
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
	private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

	private final String[] QUERY_PREFIX = {"get"};

	/**
	 * Dao aspect.
	 * com.zsls.mapper.test1
	 */
	@Pointcut("execution( * com.zsls.mapper..*.*(..))")
	public void daoAspect() {
	}

	/**
	 * Switch DataSource
	 *
	 * @param point the point
	 */
	@Before("daoAspect()")
	public void switchDataSource(JoinPoint point) {
		Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
		if (isQueryMethod) {
			DataSourceContentHolder.useSlaveDataSource();
			logger.info("Switch DataSource to [{}] in Method [{}]", DataSourceContentHolder.getDataSourceKey(),
					point.getSignature());
		}
	}

	/**
	 * Restore DataSource
	 *
	 * @param point the point
	 */
	@After("daoAspect()")
	public void restoreDataSource(JoinPoint point) {
		DataSourceContentHolder.clearDataSourceKey();
		logger.info("Restore DataSource to [{}] in Method [{}]", DataSourceContentHolder.getDataSourceKey(),
				point.getSignature());
	}


	/**
	 * Judge if method start with query prefix
	 *
	 * @param methodName
	 * @return
	 */
	private Boolean isQueryMethod(String methodName) {
		for (String prefix : QUERY_PREFIX) {
			if (methodName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
}
