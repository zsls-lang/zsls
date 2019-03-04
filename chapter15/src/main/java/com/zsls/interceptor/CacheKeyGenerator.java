package com.zsls.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 *@InterfaceName CacheKeyGenerator
 *@Description key生成器
 *@Author zsls
 *@Date 2019/3/1 17:06
 *@Version 1.0
 */
public interface CacheKeyGenerator {

	/**
	 * 获取AOP参数,生成指定缓存Key
	 *
	 * @param pjp PJP
	 * @return 缓存KEY
	 */
	String getLockKey(ProceedingJoinPoint pjp);

}
