package com.kanbujian.controller;

import com.kanbujian.dao.TransactionDao;
import com.kanbujian.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("transactions")
public class TransactionsController {
    @Autowired
    private TransactionDao transactionDao;

    @GetMapping()
    public @ResponseBody List index(@RequestBody Map params){
        System.out.println("hahah");
        Pageable pageable = new PageRequest((Integer) params.get("page"), (Integer)params.get("per"));
        List<Transaction> transactionList = (List<Transaction>) transactionDao.findAll(pageable);
        return transactionList;
    }

    @PostMapping()
    @Transactional
    public @ResponseBody Map create(){
        Transaction transaction = new Transaction("cny", 1000, "refund");
        transactionDao.save(transaction);
        System.out.println("save successful");
        return null;
    }

    @GetMapping("/{id}")
    public @ResponseBody Transaction show(){
        Transaction transaction = transactionDao.findFirstByCurrency("cny");
        return transaction;
    }

    @PutMapping("/{id}")
    public @ResponseBody Map update(){
        return null;
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Map destory(){

        return null;
    }

}
