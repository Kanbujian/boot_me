package com.kanbujian.service;

import com.kanbujian.dao.TransactionDao;
import com.kanbujian.entity.Transaction;
import com.kanbujian.payment.Action;
import com.kanbujian.payment.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {
    public static final String CHARGE_CLASS_NAME = "com.kanbujian.payment.%s.Charge";
    public static final String REFUND_CLASS_NAME = "com.kanbujian.payment.%s.Refund";
    public static final String NOTIFY_PAID_CLASS_NAME = "com.kanbujian.payment.%s.NotifyPaid";
    public static final String PARAM_CONVERTER_CLASS_NAME = "com.kanbujian.payment.%s.ParamConverter";

    @Autowired
    private TransactionDao transactionDao;

    public Transaction charge(Long transactionId) throws Exception {
        Transaction ts = transactionDao.findById(transactionId).get();
        String gateway = ts.getGateway();
        String classString =  String.format(CHARGE_CLASS_NAME, gateway);
        try {
            Class clazz = Class.forName(classString);
            Action obj = (Action) clazz.getDeclaredConstructor(Transaction.class).newInstance(ts);
            Map response = obj.run();
            System.out.println(response.toString());
            ts.getExtra().put("chargeInfo", response);
            ts.setStatus(Transaction.Status.unprocessed);
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
        String classString =  String.format(REFUND_CLASS_NAME, gateway);
        try {
            Class clazz = Class.forName(classString);
            Action obj = (Action) clazz.getDeclaredConstructor(Transaction.class).newInstance(ts);
            Map response = obj.run();
            ts.getExtra().put("refundInfo", response);
            ts.setStatus(Transaction.Status.refunded);
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

    public String notifyPaid(Long transactionId, byte[] data)throws Exception {
        Transaction ts = transactionDao.findById(transactionId).get();
        String gateway = ts.getGateway();
        String convertClassString =  String.format(PARAM_CONVERTER_CLASS_NAME, gateway);
        String notifyPaidString =  String.format(NOTIFY_PAID_CLASS_NAME, gateway);
        Map response = null;
        try {
            Map notifyParams = null;
            Class converterClazz = Class.forName(convertClassString);
            Converter converter = (Converter) converterClazz.getDeclaredConstructor().newInstance();
            notifyParams = converter.convertNotifyParams(data);

            Class clazz = Class.forName(notifyPaidString);
            Action obj = (Action) clazz.getDeclaredConstructor(Transaction.class, Map.class).newInstance(ts, notifyParams);
            response = obj.run();
            ts.getExtra().put("refundInfo", notifyParams);
            ts.setStatus(Transaction.Status.refunded);
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
        return (String) response.get("result");
    }
}
