package com.kanbujian.dao;


import com.kanbujian.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TransactionDao extends PagingAndSortingRepository<Transaction, Long> {
    Transaction findFirstByCurrency(String curreny);
}