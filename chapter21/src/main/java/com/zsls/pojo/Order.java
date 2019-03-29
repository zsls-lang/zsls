package com.zsls.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 *@Description
 * 结果的格式是按照实体类中注解走的，因为实体类的是局部变量，局部变量的优先级高于全局变量
 *
 */
public class Order {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime payTime;

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
	}

}
