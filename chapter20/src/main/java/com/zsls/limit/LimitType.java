package com.zsls.limit;

/**
 *@EnumName LimitType
 *@Description TODO redis 限流类型
 *@Author zsls
 *@Date 2019/3/27 14:41
 *@Version 1.0
 */
public enum LimitType {
	/**
	 * 自定义key
	 */
	CUSTOMER,
	/**
	 * 根据请求者IP
	 */
	IP;
}
