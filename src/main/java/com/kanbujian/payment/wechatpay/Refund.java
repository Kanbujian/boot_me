package com.kanbujian.payment.wechatpay;

import com.kanbujian.entity.Transaction;
import com.kanbujian.payment.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Refund implements Action {
    private Transaction transaction;

    public Refund(Transaction transaction) {
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
        String certPath = (String) extra.get("certPath");
        String certPwd = (String) extra.get("certPwd");
        com.kanbujian.payment.wechatpay.lib.Refund refund =  new com.kanbujian.payment.wechatpay.lib.Refund(appId, mchId, appSecret, certPath, certPwd);
        Map result = refund.run(handleParams(appId, mchId, extra));
        return result;
    }

    private Map handleParams(String appId, String mchId, Map extra){
        Map requestParams = new HashMap();
        requestParams.put("appid", appId);
        requestParams.put("mch_id", mchId);
        requestParams.put("out_trade_no", transaction.getReadableNumber());
        requestParams.put("out_refund_no", UUID.randomUUID().toString().replace("-", ""));
        requestParams.put("total_fee", transaction.getAmount());
        requestParams.put("refund_fee", transaction.getAmount());
        requestParams.put("refund_fee_type", transaction.getCurrency());
        requestParams.put("refund_desc", extra.get("refund_desc"));
        return requestParams;
    }

}
