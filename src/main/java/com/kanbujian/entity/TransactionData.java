package com.kanbujian.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionData {
    private Integer merchantId;
    private String paymentKey;

    public TransactionData(){

    }


    public TransactionData(Integer merchantId, String paymentKey) {
        this.merchantId = merchantId;
        this.paymentKey = paymentKey;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }
}
