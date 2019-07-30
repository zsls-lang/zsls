package com.zsls.event;

import org.springframework.context.ApplicationEvent;

/*****************短信发送事件源*************/
public class SendMessageEvent extends ApplicationEvent {

    private String phoneNum;

    public SendMessageEvent(Object  source,String phoneNum ) {
        super(source);
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}