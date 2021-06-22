package com.zsls.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

public class DemoContoller {
    public static void main(String[] args) {
        //加载配置
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //获取ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();

        //获取ProcessEngine对象,默认根据activiti.cfg.xml文件加载配置文件
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    }


}
