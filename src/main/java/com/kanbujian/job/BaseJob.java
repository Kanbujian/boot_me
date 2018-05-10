package com.kanbujian.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.beans.Introspector;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class BaseJob implements Job{
    private final Logger logger = LoggerFactory.getLogger(BaseJob.class);
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private RedisTemplate redisTemplate;
    private String taskId;

    public BaseJob() {
    }

    public String perform(){
        taskId = taskId();
        executorService.submit(()->{
            String res = null;
            try {
                res = run();
                cacheResult(res);
            } catch (Exception e) {
                cacheResult(e.getMessage());
            }
            logger.debug("result is " + res);
        });
        logger.debug("task id is " + taskId);
        return taskId;
    }

    public String taskId(){
        return UUID.randomUUID().toString() + this.getClass().getName();
    }

    public void cacheResult(String result){
        logger.debug("redisTemplet is " + redisTemplate);
        redisTemplate.opsForValue().set(taskId, result, 1, TimeUnit.HOURS);
    }
}
