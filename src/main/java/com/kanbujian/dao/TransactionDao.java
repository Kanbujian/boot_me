package com.kanbujian.dao;


import com.kanbujian.entity.Transaction;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TransactionDao extends Repository<Transaction, Long> {
    List<Transaction> findAll();

    void save(Transaction transaction);
}