package com.zsls.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TaskService {


    /*
     *在 Quartz 中， scheduler 由 scheduler 工厂创建：
     * DirectSchedulerFactory 或者 StdSchedulerFactory。
     *第二种工厂 StdSchedulerFactory 使用较多，
     *因为 DirectSchedulerFactory 使用起来不够方便，需要作许多详细的手工编码设置。
     */
    public void haveProperties() throws SchedulerException, InterruptedException {

        // 获取scheduler实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        System.out.println(" scheduler.start");

        // 具体任务
        JobDetail jobDetail = JobBuilder.newJob(HelloJobOne.class).withIdentity("job1", "group1").build();

        // 触发时间 (每5秒执行1次.)
        ScheduleBuilder  simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().withSchedule(simpleScheduleBuilder).build();

        // 交由scheduler 安排触发
        scheduler.scheduleJob(jobDetail,trigger);
        //睡眠20秒.
        TimeUnit.SECONDS.sleep(20);
        // 关闭定时任务调度
        scheduler.shutdown();
        System.out.println("scheduler.shutdown");
    }
}
