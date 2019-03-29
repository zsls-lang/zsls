package com.zsls.pojo;

import java.time.LocalDateTime;

/**
 *@ClassName Order
 *@Description TODO
 *@Author zsls
 *@Date 2019/3/29 15:25
 *@Version 1.0
 */
public class Order {

	//@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime payTime;

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
	}

}
