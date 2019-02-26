package com.zsls.interceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zsls.annotation.LocalLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 *@ClassName LockMethodInterceptor
 *@Description 本地缓存
 *@Author zsls
 *@Date 2019/2/26 16:55
 *@Version 1.0
 */
@Configuration
@Aspect
public class LockMethodInterceptor {

	private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
			//最大缓存 100 个
			.maximumSize(1000)
			//设置写缓存后 5 秒钟过期
			.expireAfterWrite(5, TimeUnit.SECONDS).build();

	@Around("execution(public * *(..)) && @annotation(com.zsls.annotation.LocalLock)")
	public Object interceptor(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		LocalLock localLock = method.getAnnotation(LocalLock.class);
		String key = getKey(localLock.key(), pjp.getArgs());
		if (!StringUtils.isEmpty(key)) {
			if (null != CACHES.getIfPresent(key)) {
				throw new RuntimeException("请勿重复请求");
			}
			//第一次进入 当前key 当做对象放入cache
			CACHES.put(key, key);
		}
		try {
			return pjp.proceed();
		} catch (Throwable throwable) {
			throw new RuntimeException("服务异常");
		}finally{
			// TODO 这里就不调用 CACHES.invalidate(key); 代码了
		}
	}

	/**
	 * @Author zsls
	 * @Description key 的生成策略，如果想灵活可以写成接口与实现类的方式
	 * @Date 17:43 2019/2/26
	 * @Param [keyExpress, args]
	 * @return java.lang.String
	 */
	private String getKey(String keyExpress, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
		}
		return keyExpress;
	}

}
