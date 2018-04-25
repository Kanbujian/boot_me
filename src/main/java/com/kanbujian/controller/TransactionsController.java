package com.kanbujian.controller;

import com.kanbujian.dao.TransactionDao;
import com.kanbujian.entity.Transaction;
import com.kanbujian.entity.TransactionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController()
@Transactional
@RequestMapping("transactions")
public class TransactionsController {
    @Autowired
    private TransactionDao transactionDao;

    @GetMapping()
    public @ResponseBody Page index(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "per", defaultValue = "20") Integer per){
        Pageable pageable = PageRequest.of(page, per);
        Page<Transaction> transactionList = transactionDao.findAll(pageable);
        return transactionList;
    }

    @PostMapping()
    @Transactional
    public @ResponseBody Transaction create(@RequestBody Transaction transaction){
        // Transaction transaction = new Transaction("cny", 200, "refund");
        // TransactionData transactionData = new TransactionData(1002032, "Back to december");
        // transaction.setTransactionData(transactionData);
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setEventType("charge");
        transactionLog.setTransaction(transaction);
        List<TransactionLog> logs = transaction.getTransactionLogs();
        if (logs == null){
            logs = new ArrayList<TransactionLog>();
        }
        logs.add(transactionLog);
        transaction.setTransactionLogs(logs);
        transactionDao.save(transaction);
        return transaction;
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Optional<Transaction> show(@PathVariable(name = "id")Long id){
        Optional<Transaction> transaction = transactionDao.findById(id);
        return transaction;
    }

    @PutMapping("/{id}")
    public @ResponseBody Map update(@PathVariable(name = "id")Long id, @RequestBody Transaction transactionParams){
        Optional<Transaction> transaction = transactionDao.findById(id);
        transactionDao.save(transactionParams);
        return null;
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Map destory(@PathVariable(name = "id")Long id){
        transactionDao.deleteById(id);
        return null;
    }

}
