package com.zsls.config;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *监听器分配模式
 *手动创建类实现监听接口,重写方法
 */
public class MYListener implements TaskListener {
    Logger logger =  LoggerFactory.getLogger(MYListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info(":::::::::MYListener:notify begin:::::::::");
        delegateTask.setAssignee("kangge");
        logger.info(":::::::::MYListener:notify end:::::::::");
    }
}