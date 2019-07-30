package com.zsls.listener2;

import com.zsls.event.SendMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//这里使用泛型
@Async("taskExecutor")
@Component
public class MessageRegisterListener implements ApplicationListener<SendMessageEvent> {
	private final static Logger LOGGER = LoggerFactory.getLogger(MessageRegisterListener.class);

	//因为使用了泛型，我们的重写方法入参事件就唯一了。
	@Override
	public void onApplicationEvent(SendMessageEvent event) {
		LOGGER.info("正在向{}发送邮件......", event.getPhoneNum());//模拟发送邮短信事件
		try {
			Thread.sleep(1 * 1000);//模拟发送短信过程
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("短信发送成功!");
	}
	// ....
}