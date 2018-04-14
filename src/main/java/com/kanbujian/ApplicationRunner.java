package com.kanbujian;

import com.kanbujian.controller.MessageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationRunner {
    public static void main(String[] args){
        SpringApplication.run(ApplicationRunner.class, args);
    }

}
