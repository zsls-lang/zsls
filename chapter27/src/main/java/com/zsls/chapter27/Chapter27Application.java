package com.zsls.chapter27;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author zsls
 * @Description
 *  eg: savetestBean5()
 * 在没有加 jta-atomikos 之前 插入两个数据库的数据 一个插入进去另一个没有插入进去 不是同一个事务
 * 加入jta-atomikos 断点查看，当执行完第一个库的插入时，刷新数据库表是没有数据的，
 * 	 * 这表明这连个事务处于同一个事务中，执行到报错一行后，
 * 	 * 查看数据库，没有插入数据，这表明整合分布式事务成功
 * @Param
 * @return
 */
@SpringBootApplication
public class Chapter27Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter27Application.class, args);
	}

}
