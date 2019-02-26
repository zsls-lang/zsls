package com.zsls.annotation;

import java.lang.annotation.*;

/**
 *@ClassName LocalLock
 *@Description 锁的注解
 *@Author zsls
 *@Date 2019/2/26 16:24
 *@Version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {

	/**
	 * @author fly
	 */
	String key() default "";

	/**
	 * 过期时间 TODO 由于用的 guava 暂时就忽略这属性吧 集成 redis 需要用到
	 *
	 * @author fly
	 */
	int expire() default 5;
}
