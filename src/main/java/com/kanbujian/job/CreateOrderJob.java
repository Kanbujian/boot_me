package com.kanbujian.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class CreateOrderJob extends BaseJob {
    private final Logger logger = LoggerFactory.getLogger(CreateOrderJob.class);

    @Override
    public String run() throws Exception {
        // mock the
        Thread.sleep(10000l);
        logger.debug("job thread is " + Thread.currentThread().getName());
        return "success";
    }
}
