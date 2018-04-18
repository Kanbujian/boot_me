package com.kanbujian.controller;

import com.kanbujian.dao.TransactionDao;
import com.kanbujian.entity.Transaction;
import com.kanbujian.entity.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController()
@Transactional
@RequestMapping("transactions")
public class TransactionsController {
    @Autowired
    private TransactionDao transactionDao;

    @GetMapping()
    public @ResponseBody Page index(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "per", defaultValue = "20") Integer per){
        Pageable pageable = new PageRequest(page, per);
        Page<Transaction> transactionList = transactionDao.findAll(pageable);
        return transactionList;
    }

    @PostMapping()
    @Transactional
    public @ResponseBody Map create(@RequestBody Transaction transaction){
        // Transaction transaction = new Transaction("cny", 200, "refund");
        // TransactionData transactionData = new TransactionData(1002032, "Back to december");
        // transaction.setTransactionData(transactionData);
        transactionDao.save(transaction);
        return null;
    }

    @GetMapping("/{id}")
    public @ResponseBody Transaction show(@PathVariable(name = "id")Long id){
        Transaction transaction = transactionDao.findOne(id);
        return transaction;
    }

    @PutMapping("/{id}")
    public @ResponseBody Map update(@PathVariable(name = "id")Long id, @RequestBody Transaction transactionParams){
        Transaction transaction = transactionDao.findOne(id);
        transactionDao.save(transactionParams);
        return null;
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Map destory(@PathVariable(name = "id")Long id){
        transactionDao.delete(id);
        return null;
    }

}
