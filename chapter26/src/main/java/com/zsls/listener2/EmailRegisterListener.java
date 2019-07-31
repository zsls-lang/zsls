package com.zsls.listener2;

import com.zsls.event.SendEmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//这里使用泛型
@Async("taskExecutor")
@Component
public class EmailRegisterListener implements ApplicationListener<SendEmailEvent> {
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailRegisterListener.class);

	//因为使用了泛型，我们的重写方法入参事件就唯一了。
	@Override
	public void onApplicationEvent(SendEmailEvent event) {
		LOGGER.info("正在向{}发送邮件......", event.getEmailAddress());//模拟发送邮件事件
		try {
			Thread.sleep(1 * 1000);//模拟请求邮箱服务器、验证账号密码，发送邮件耗时。
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("邮件发送成功!");
	}
	// ....
}