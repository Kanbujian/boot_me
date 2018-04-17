package com.kanbujian;

import com.kanbujian.controller.MessageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.kanbujian.dao")
@EntityScan("com.kanbujian.entity")
@ComponentScan("com.kanbujian")
public class ApplicationRunner {
    public static void main(String[] args){
        SpringApplication.run(ApplicationRunner.class, args);
    }

}
