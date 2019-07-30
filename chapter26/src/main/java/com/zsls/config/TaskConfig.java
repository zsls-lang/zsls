package com.zsls.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
//https://www.cnblogs.com/jebysun/p/9675345.html
@Configuration
public class TaskConfig {
    public TaskConfig() {
    }

    @Bean("taskExecutor")
    @ConditionalOnMissingBean({ThreadPoolTaskExecutor.class})
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
        taskExecutor.setCorePoolSize(100);
		// 设置最大线程数
        taskExecutor.setMaxPoolSize(800);
		// 设置队列容量
        taskExecutor.setQueueCapacity(100);
//		// 设置线程活跃时间（秒）
		taskExecutor.setKeepAliveSeconds(60);
		// 设置默认线程名称
		taskExecutor.setThreadNamePrefix("taskExecutor-");
//		// 设置拒绝策略  线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//		// 等待所有任务结束后再关闭线程池 调度器shutdown被调用时等待当前被调度的任务完成
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//		//等待时长
		taskExecutor.setAwaitTerminationSeconds(60);
		taskExecutor.initialize();
        return taskExecutor;
    }

}