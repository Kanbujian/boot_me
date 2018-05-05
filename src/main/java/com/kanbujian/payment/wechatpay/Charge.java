package com.kanbujian.payment.wechatpay;

import com.kanbujian.entity.Transaction;
import com.kanbujian.entity.TransactionData;

import java.util.Map;

public class Charge {
    private Transaction transaction;

    public Charge(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void run(){
        Map extra = transaction.getExtra();
    }

}
