package com.kanbujian.job;

import org.springframework.data.redis.core.RedisTemplate;

import java.beans.Introspector;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class BaseJob implements Job{
    private ExecutorService executorService;
    private RedisTemplate redisTemplate;
    private String taskId;

    public BaseJob() {
    }

    public BaseJob(ExecutorService executorService, RedisTemplate redisTemplate){
       this.executorService = executorService;
       this.redisTemplate = redisTemplate;
       this.taskId = taskId();
    }

    public String perform(){
        executorService.submit(()->{
            String res = run();
            System.out.println("result is " + res);
            cacheResult(res);
        });
        System.out.println("task id is " + taskId);
        return taskId;
    }

    public String taskId(){
        return UUID.randomUUID().toString() + this.getClass().getName();
    }

    public void cacheResult(String result){
        redisTemplate.opsForValue().set(taskId, result);
    }
}
