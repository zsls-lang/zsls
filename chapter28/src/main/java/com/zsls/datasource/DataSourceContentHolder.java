package com.zsls.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@ClassName DataSourceContentHolder
 *@Description
 *@Author zsls
 *@Date 2019/9/11 12:42
 *@Version 1.0
 */
public class DataSourceContentHolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceContentHolder.class);
	// 使用ThreadLocal保证线程安全
	private static final ThreadLocal<DataSourceType> CONTENT_HOLDER = ThreadLocal.withInitial(()->DataSourceType.DB1);

	// 往当前线程里设置数据源类型
	public static void setDataSourceType(DataSourceType dataSourceType) {
		if (dataSourceType == null) {
			throw new NullPointerException();
		}
		LOGGER.info("[将当前数据源改为]：{}", dataSourceType);
		CONTENT_HOLDER.set(dataSourceType);
	}

	// 获取数据源类型
	public static DataSourceType getDataSourceType() {
		LOGGER.info("[获取当前数据源的类型为]：{}", CONTENT_HOLDER.get());
		return CONTENT_HOLDER.get();
	}

	// 清空数据类型
	public static void clearDataSourceType() {
		CONTENT_HOLDER.remove();
	}

}
