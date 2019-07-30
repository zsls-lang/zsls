package com.zsls.service;

import com.zsls.event.SendEmailEvent;
import com.zsls.event.SendMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class UserService2 implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService2.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void doLogin(String emailAddress,String phoneNum) throws InterruptedException{
        Thread.sleep(200);//模拟用户注册的相关业务逻辑处理
		LOGGER.info("注册成功！");
        //下列向用户发送邮件
        SendEmailEvent sendEmailEvent = new SendEmailEvent(this,emailAddress);//定义事件
		SendMessageEvent sendMessageEvent = new SendMessageEvent(this, phoneNum);
        applicationContext.publishEvent(sendEmailEvent);//发布事件
        applicationContext.publishEvent(sendMessageEvent);
    }
    //...忽略其他用户管理业务方法
}