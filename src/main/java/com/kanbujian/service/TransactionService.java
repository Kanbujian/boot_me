package com.kanbujian.service;

import com.kanbujian.dao.TransactionDao;
import com.kanbujian.entity.Transaction;
import com.kanbujian.payment.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    public Transaction charge(Long transactionId) throws Exception {
        Transaction ts = transactionDao.findById(transactionId).get();
        String gateway = ts.getGateway();
        String classString =  String.format("com.kanbujian.payment.%s.Charge", gateway);
        try {
            Class clazz = Class.forName(classString);
            Action obj = (Action) clazz.getDeclaredConstructor(Transaction.class).newInstance(ts);
            Map response = obj.run();
            System.out.println(response.toString());
            ts.getExtra().put("chargeInfo", response);
            transactionDao.save(ts);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ts;
    }

    public Transaction refund(Long transactionId, Map refundParams) throws Exception {
        Transaction ts = transactionDao.findById(transactionId).get();
        String gateway = ts.getGateway();
        ts.getExtra().putAll(refundParams);
        String classString =  String.format("com.kanbujian.payment.%s.Refund", gateway);
        try {
            Class clazz = Class.forName(classString);
            Action obj = (Action) clazz.getDeclaredConstructor(Transaction.class).newInstance(ts);
            Map response = obj.run();
            System.out.println(response.toString());
            ts.getExtra().put("refundInfo", response);
            transactionDao.save(ts);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ts;
    }
}
