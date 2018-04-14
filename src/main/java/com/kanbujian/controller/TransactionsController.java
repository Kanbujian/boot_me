package com.kanbujian.controller;

import com.kanbujian.dao.impl.TransactionDaoImpl;
import com.kanbujian.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("transactions")
public class TransactionsController {
    @Autowired
    private TransactionDaoImpl transactionDao;

    @GetMapping()
    public @ResponseBody List index(){
        List<Transaction> transactionList= transactionDao.findAll();
        System.out.println("hahah");
        return transactionList;
    }

    @PostMapping()
    @Transactional
    public @ResponseBody Map create(){
        Transaction transaction = new Transaction("cny", 500, "chare");
        transactionDao.save(transaction);
        System.out.println("save successful");
        return null;
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
