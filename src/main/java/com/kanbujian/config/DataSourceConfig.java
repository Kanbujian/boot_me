package com.kanbujian.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.data.repository.query.QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

@Configuration
@EnableJpaRepositories(value = "com.acme.dao",queryLookupStrategy = CREATE_IF_NOT_FOUND)
public class dataSource {
}
