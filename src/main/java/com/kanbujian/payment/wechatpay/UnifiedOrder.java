package com.kanbujian.payment.wechatpay;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class UnifiedOrder {
    private final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private String appId;
    private String mchId;
    private String appSecret;

    public UnifiedOrder(String appId, String mchId, String appSecret) {
        this.appId = appId;
        this.mchId = mchId;
        this.appSecret = appSecret;
    }

    public void run(Map extra) throws IOException {

        OkHttpClient httpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"), handleParams(extra));
        Request request = new Request.Builder().url(URL).post(body).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            ResponseBody requestBody = response.body();
            // requestBody.string()
        }
    }

    private String handleParams(Map extra){
        extra.put("appid", appId);
        extra.put("mch_id", mchId);
        extra.put("sign", SignUtil.sign(extra, appSecret));
        return SignUtil.toXml(extra);
    }
}
