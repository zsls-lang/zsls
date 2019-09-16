package com.zsls.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *@ClassName DynamicDataSource
 *@Description
 *@Author zsls
 *@Date 2019/9/11 15:42
 *@Version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

	/**
	 * Set dynamic DataSource to Application Context
	 *
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		logger.info("Current DataSource is [{}]", DataSourceContentHolder.getDataSourceKey());
		return DataSourceContentHolder.getDataSourceKey();
	}
}
