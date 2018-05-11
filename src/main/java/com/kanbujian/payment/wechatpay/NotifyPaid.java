package com.kanbujian.payment.wechatpay;

import com.kanbujian.entity.Transaction;
import com.kanbujian.payment.Action;
import com.kanbujian.payment.wechatpay.lib.ResponseException;
import com.kanbujian.payment.wechatpay.lib.SignUtil;

import java.util.Map;

public class NotifyPaid implements Action {
    private Transaction transaction;
    private Map notifyParams;

    public NotifyPaid(Transaction transaction, Map notifyParams) {
        this.transaction = transaction;
        this.notifyParams = notifyParams;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Map getNotifyParams() {
        return notifyParams;
    }

    public void setNotifyParams(Map notifyParams) {
        this.notifyParams = notifyParams;
    }

    @Override
    public Map run() throws Exception {
        if (checkNotifyParams()){
            transaction.setStatus(Transaction.Status.charged);
            transaction.getExtra().put("notifyPaidInfo", notifyParams);
            // notify other service
        }else{
            throw new ResponseException("SIGN_ERROR", "ss", "sdsd");
        }
        return null;
    }

    private boolean checkNotifyParams(){
        String sign = (String) notifyParams.remove("sign");
        String appSecret =  (String) transaction.getExtra().get("paymentKey");
        if(sign != null && sign.equals(SignUtil.sign(notifyParams, appSecret))
                && transaction.getAmount().equals(notifyParams.get("total_fee"))){
            return true;
        }else{
            return false;
        }
    }
}
