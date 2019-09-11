package com.zsls.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *@ClassName DynamicDataSource
 *@Description
 *@Author zsls
 *@Date 2019/9/11 15:42
 *@Version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContentHolder.getDataSourceType();
	}
}
