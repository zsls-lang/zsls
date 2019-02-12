package com.zsls.timer;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 *@ClassName TimerDemo
 *@Description 基于Timer实现的定时调度（不推荐，用该方式不如用 ScheduledExecutorService ）
 *@Author zsls
 *@Date 2019/2/12 16:33
 *@Version 1.0
 */
public class TimerDemo {
	public static void main(String[] args) {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				System.out.println("执行任务" + LocalDateTime.now());
			}
		};
		Timer timer = new Timer();
		//	// timerTask：需要执行的任务
		//	// delay：延迟时间（以毫秒为单位）
		//	// period：间隔时间（以毫秒为单位）
		timer.schedule(timerTask,5000,3000);
	}
}
