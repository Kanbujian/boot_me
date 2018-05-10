package com.kanbujian.dao;

import com.kanbujian.entity.App;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AppDao extends PagingAndSortingRepository<App, Long> {
    Optional<App> findByToken(String token);
}
