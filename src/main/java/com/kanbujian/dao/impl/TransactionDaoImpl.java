package com.kanbujian.dao.impl;

import com.kanbujian.dao.TransactionDao;
import com.kanbujian.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TransactionDaoImpl implements TransactionDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Transaction> findAll() {
        // entityManager.
        return null;
    }

    @Override
    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }
}
