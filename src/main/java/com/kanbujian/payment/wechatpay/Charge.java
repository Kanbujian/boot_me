package com.kanbujian.payment.wechatpay;

import com.kanbujian.entity.Transaction;
import com.kanbujian.entity.TransactionData;
import com.kanbujian.payment.Action;

import java.io.IOException;
import java.util.Map;

public class Charge implements Action {
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

    public Map run() throws Exception {
        UnifiedOrder unifiedOrder =  new UnifiedOrder(transaction);
        unifiedOrder.run();
        return null;
    }

}
