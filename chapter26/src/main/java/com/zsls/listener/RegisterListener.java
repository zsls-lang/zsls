package com.zsls.listener;

import com.zsls.event.SendEmailEvent;
import com.zsls.event.SendMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 测试listener2时注释这个 @Component
 */
//@Component
//@Async
public class RegisterListener implements ApplicationListener {
	private final  static Logger LOGGER = LoggerFactory.getLogger(RegisterListener.class);
    /*
    *当我们的发布者发布时间时，我们的监听器收到信号，就会调用这个方法
    *我们对其进行重写来适应我们的需求
    *@Param event:我们的事件源
    */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //我们定义了两个事件：发短信，发邮箱，他们一旦被发布都会被此方法调用
        //于是我们需要判断当前event的具体类型
        if(event instanceof SendEmailEvent){//如果是发邮箱事件
			LOGGER.info("正在向{}发送邮件......",((SendEmailEvent) event).getEmailAddress());//模拟发送邮件事件
            try {
                Thread.sleep(1* 1000);//模拟请求邮箱服务器、验证账号密码，发送邮件耗时。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			LOGGER.info("邮件发送成功!");
        }else if(event instanceof SendMessageEvent){//是发短信事件
			LOGGER.info("正在向{}发送邮件......",((SendMessageEvent) event).getPhoneNum());//模拟发送邮短信事件
            try {
                Thread.sleep(1* 1000);//模拟发送短信过程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			LOGGER.info("短信发送成功!");
        }
    }

}