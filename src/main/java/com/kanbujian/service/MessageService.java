package com.kanbujian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Async
    public CompletableFuture<String> create(Map params) throws InterruptedException {
        Thread.sleep(3000l);
        redisTemplate.opsForValue().set(params.get("sender"), params.get("content"));
        String content = (String) redisTemplate.opsForValue().get(params.get("sender"));
        redisTemplate.opsForValue().set("status", "success");
        stringRedisTemplate.opsForValue().set("ddd", "ttttt");
        return CompletableFuture.completedFuture(content);
    }
}
