package com.kanbujian.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class WorkerConfig {

    @Value("${task.worker.corePoolSize}")
    private int corePoolSize;
    @Value("${task.worker.queueCapacity}")
    private int queueCapacity;


    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
