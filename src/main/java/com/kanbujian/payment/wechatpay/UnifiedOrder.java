package com.kanbujian.payment.wechatpay;

import com.kanbujian.entity.Transaction;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UnifiedOrder {
    private final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private Transaction transaction;
    private String appId;
    private String mchId;
    private String appSecret;

    public UnifiedOrder(Transaction transaction) {
        this.transaction = transaction;
        Map extra = transaction.getExtra();
        this.appId = (String) extra.get("appId");
        this.mchId = (String) extra.get("mchId");
        this.appSecret = (String) extra.get("paymentKey");
    }

    public UnifiedOrder(String appId, String mchId, String appSecret) {
        this.appId = appId;
        this.mchId = mchId;
        this.appSecret = appSecret;
    }

    public Map run() throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"), handleParams());
        Request request = new Request.Builder().url(URL).post(body).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            ResponseBody requestBody = response.body();

            System.out.println(" the result is ");
            System.out.println(requestBody.string());

            Map res = XmlUtil.dom2Map(requestBody.string());
            if (SignUtil.isValid(res, appSecret)){
                return res;
            }else{
                // some error
            }
        }
        return null;
    }

    private String handleParams(){
        Map requestParams = new HashMap();
        Map extra = transaction.getExtra();
        requestParams.put("appid", appId);
        requestParams.put("mch_id", mchId);
        requestParams.put("openid", extra.get("open_id"));
        requestParams.put("out_trade_no", transaction.getReadableNumber());
        requestParams.put("notify_url", transaction.getNotifyUrl());
        requestParams.put("body",  extra.get("product_ame"));
        requestParams.put("trade_type", extra.get("trade_type"));
        requestParams.put("spbill_create_ip", extra.get("spbill_create_ip"));
        requestParams.put("total_fee", transaction.getAmount());
        requestParams.put("nonce_str", SignUtil.nonceStr());


        requestParams.put("sign", SignUtil.sign(requestParams, appSecret));

        System.out.println("the request is " + SignUtil.toXml(requestParams));
        return SignUtil.toXml(requestParams);
    }
}
