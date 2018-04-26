package com.kanbujian.controller;

import com.kanbujian.job.BaseJob;
import com.kanbujian.job.CreateOrderJob;
import com.kanbujian.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private CreateOrderJob createOrderJob;

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

    @GetMapping("/retrieve")
    public @ResponseBody String retrieve(@RequestParam(name = "jobId") String jobId){
        String jobResult = (String) redisTemplate.opsForValue().get(jobId);
        if (jobResult == null) {
            return "retry";
        }else {
            return jobResult;
        }
    }

    @PostMapping("/messages")
    public @ResponseBody String create() throws InterruptedException, ExecutionException {
        Map params = new HashMap(){{
            put("sender", "jack");
            put("content", "hello");
        }};
        logger.debug("controller thread" + Thread.currentThread().getName());
        String jobId = createOrderJob.perform();
        return jobId;
    }
}
