package com.kanbujian.job;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ExecutorService;

public class CreateOrderJob extends BaseJob {

    public CreateOrderJob(ExecutorService executorService, RedisTemplate redisTemplate) {
        super(executorService, redisTemplate);
    }

    @Override
    public String run() {
        try{
            Thread.sleep(10000l);
        }catch(Exception ex){
            // do nothing
        }
        System.out.println("job thread is " + Thread.currentThread().getName());
        return "success";
    }
}
