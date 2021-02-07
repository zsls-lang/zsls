package com.zsls.task;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 2开始从定时器工厂中，找到定时器Bean
 */
//@Configuration
//public class QuartzConfig {
//    @Autowired
//    private JobFactory jobFactory;
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(){
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setOverwriteExistingJobs(true);
//        schedulerFactoryBean.setJobFactory(jobFactory);
//        return schedulerFactoryBean;
//    }
//
//    // 创建scheduler
//    @Bean(name = "scheduler")
//    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean){
//        return schedulerFactoryBean.getObject();
//    }
//
//}
