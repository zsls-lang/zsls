package com.zsls.task;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SimpleScheduleBuilder是简单调用触发器，它只能指定触发的间隔时间和执行次数；
 * CronScheduleBuilder是类似于Linux Cron的触发器，
 * 它通过一个称为CronExpression的规则来指定触发规则，通常是每次触发的具体时间；（关于CronExpression，详见：官方，中文网文）
 * CalendarIntervalScheduleBuilder是对CronScheduleBuilder的补充，它能指定每隔一段时间触发一次。
 */
@Service
public class QuartzService {

    @Autowired
    private Scheduler scheduler;

    public void startJob(String time, String jobName, String group, Class job) {
        try {
            // 创建jobDetail实例 绑定job实现类 指明job的名称 所在组的名称 及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, group).build();
            // cron 表达式
            ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            // 设定定时任务的时间触发规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, group).withSchedule(scheduleBuilder).build();

            System.out.println("startJob.scheduler.getSchedulerName:" + scheduler.getSchedulerName());
            // 把jobdetail和trigger注册到任务调度scheduler中
            scheduler.scheduleJob(jobDetail,trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public void startJob2(String time, String jobName, String group, Class job) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, group).build();//设置Job的名字和组
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, group).withSchedule(scheduleBuilder).build();
            System.out.println("startJob2.scheduler.getSchedulerName:" + scheduler.getSchedulerName());
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停一个任务
     * @param triggerName
     * @param triggerGroupName
     */
    public void pauseJob(String triggerName,String triggerGroupName){
        try {
            JobKey jobKey = new JobKey(triggerName,triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail == null ){
                return;
            }
            System.out.println("pauseJob一个定时器");
            scheduler.pauseJob(jobKey);
            System.out.println("");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 重启暂停的一个定时任务
     * @param triggerName
     * @param triggerGroupName
     */
    public void resumeJob(String triggerName,String triggerGroupName){
        try {
            JobKey jobKey = new JobKey(triggerName,triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail == null ){
                return;
            }
            System.out.println("resumeJob一个定时器");
            scheduler.resumeJob(jobKey);
            System.out.println("");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    /****
     * 删除一个定时器任务，删除了，重启就没什么用了
     * @param triggerName
     * @param triggerGroupName
     */
    public void deleteJob(String triggerName, String triggerGroupName) {
        try {
            JobKey jobKey = new JobKey(triggerName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            System.out.println("deleteJob一个定时器");
            scheduler.deleteJob(jobKey);
            System.out.println("");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据出发规则匹配任务，立即执行定时任务，暂停的时候可以用
     */
    public void doJob(String triggerName, String triggerGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(triggerName, triggerGroupName);
            System.out.println("doJob一个定时器");
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开启定时器，这时才可以开始所有的任务，默认是开启的
     */
    public void startAllJob() {
        try {
            scheduler.start();
            System.out.println("");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭定时器，则所有任务不能执行和创建
     */
    public void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
