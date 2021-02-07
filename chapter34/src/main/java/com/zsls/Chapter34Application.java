package com.zsls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Trigger（触发器）：触发任务任务执行的时间或规则。在任务调度Quartz中，
 *     Trigger主要的触发器有：SimpleTrigger，CalendarIntervelTrigger，DailyTimeIntervalTrigger，CronTrigger
 * Scheduler（任务调度器）：Scheduler就是任务调度控制器，需要把JobDetail和Trigger注册到schedule中，才可以执行 ；
 *     Scheduler有两个重要组件：ThreadPool和JobStore。
 * Job（任务）：是一个接口，其中只有一个execute方法。开发者只要实现接口中的execute方法即可。
 * JobDetail（任务细节）：Quartz执行Job时，需要新建Job实例，但不能直接操作Job类，所以通过JobDetail获得Job的名称，描述信息
 */
@SpringBootApplication
public class Chapter34Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter34Application.class, args);
	}

}
