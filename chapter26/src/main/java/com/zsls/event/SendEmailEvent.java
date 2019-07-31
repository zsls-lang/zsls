package com.zsls.event;

import org.springframework.context.ApplicationEvent;

/*****************邮件发送事件源*************/
public class SendEmailEvent extends ApplicationEvent {
	//定义事件的核心成员：发送目的地，共监听器调用完成邮箱发送功能
	private String emailAddress;

	public SendEmailEvent(Object  source,String emailAddress ) {
		//source字面意思是根源，意指发送事件的根源，即我们的事件发布者
		super(source);
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
}
