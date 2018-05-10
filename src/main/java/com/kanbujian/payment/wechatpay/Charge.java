package com.kanbujian.payment.wechatpay;

import com.kanbujian.entity.Transaction;
import com.kanbujian.payment.Action;
import com.kanbujian.payment.wechatpay.lib.UnifiedOrder;

import java.util.HashMap;
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
        Map extra = transaction.getExtra();
        String appId = (String) extra.get("appId");
        String mchId = (String) extra.get("mchId");
        String appSecret = (String) extra.get("paymentKey");
        UnifiedOrder unifiedOrder =  new UnifiedOrder(appId, mchId, appSecret);
        Map result = unifiedOrder.run(handleParams(appId, mchId, extra));
        return result;
    }

    private Map handleParams(String appId, String mchId, Map extra){
        Map requestParams = new HashMap();
        requestParams.put("appid", appId);
        requestParams.put("mch_id", mchId);
        requestParams.put("openid", extra.get("open_id"));
        requestParams.put("out_trade_no", transaction.getReadableNumber());
        requestParams.put("notify_url", transaction.getNotifyUrl());
        requestParams.put("body",  extra.get("product_ame"));
        requestParams.put("trade_type", extra.get("trade_type"));
        requestParams.put("spbill_create_ip", extra.get("spbill_create_ip"));
        requestParams.put("total_fee", transaction.getAmount());
        return requestParams;
    }

}
