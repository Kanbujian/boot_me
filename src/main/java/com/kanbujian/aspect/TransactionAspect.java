package com.kanbujian.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransactionAspect {
    private Logger logger = LoggerFactory.getLogger(TransactionAspect.class);

    @Before("execution (* com.kanbujian.controller.*.*(..))")
    private void dispatchTransaction(){
        logger.debug("dispatch transaction");
    }
}

