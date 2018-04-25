package com.kanbujian.controller;

import com.kanbujian.job.CreateOrderJob;
import com.kanbujian.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hi")
    public @ResponseBody Map sayHi(){
        Map map = new HashMap(){{
            put("name", "nikan");
            put("age", 20);
        }};
       return map;
    }

    @Async
    @GetMapping("/retrieve")
    public @ResponseBody String retrieve() throws InterruptedException, ExecutionException {
        Map params = new HashMap(){{
           put("sender", "jack");
           put("content", "hello");
        }};
        String jobId = UUID.randomUUID().toString();
        params.put("job_id", jobId);
        CompletableFuture<String> result = messageService.create(params);
        return jobId;
    }

    @PostMapping("/messages")
    public @ResponseBody String create() throws InterruptedException, ExecutionException {
        Map params = new HashMap(){{
            put("sender", "jack");
            put("content", "hello");
        }};
        System.out.println("controller thread" + Thread.currentThread().getName());
        String jobId = new CreateOrderJob(executorService, redisTemplate).perform();
        return jobId;
    }
}
