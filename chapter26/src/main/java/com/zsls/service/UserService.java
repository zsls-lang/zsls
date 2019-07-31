package com.zsls.service;

import com.zsls.event.SendEmailEvent;
import com.zsls.event.SendMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**********方法一：实现除了通过实现ApplicationEventPublisherAware接口************/
@Service
public class UserService implements ApplicationEventPublisherAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private ApplicationEventPublisher applicationEventPublisher;//底层事件发布者

    @Override
    public void setApplicationEventPublisher(//通过Set方法完成我们的实际发布者注入
            ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void doLogin(String emailAddress,String phoneNum) throws InterruptedException{
        Thread.sleep(200);//模拟用户注册的相关业务逻辑处理
		LOGGER.info("注册成功！");
        //下列向用户发送邮件
        SendEmailEvent sendEmailEvent = new SendEmailEvent(this,emailAddress);//定义事件
		SendMessageEvent sendMessageEvent = new SendMessageEvent(this, phoneNum);
        applicationEventPublisher.publishEvent(sendEmailEvent);//发布事件
        applicationEventPublisher.publishEvent(sendMessageEvent);
    }
    //...忽略其他用户管理业务方法
}