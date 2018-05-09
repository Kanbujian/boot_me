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

    public Map run(Map params) throws IOException {
        params.put("nonce_str", SignUtil.nonceStr());
        params.put("sign", SignUtil.sign(params, appSecret));

        OkHttpClient httpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"), SignUtil.toXml(params));
        Request request = new Request.Builder().url(URL).post(body).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            ResponseBody requestBody = response.body();
            String data = requestBody.string();
            Map res = XmlUtil.dom2Map(data);
            if (SignUtil.isValid(res, appSecret)){
                if("SUCCESS".equals(res.get("result_code"))){
                    return res;
                }else{
                    throw new ResponseException(res);
                }
            }else{
                throw new ResponseException("SIGN_ERROR", "签名错误", "自己检查一波儿");
            }
        }
        return null;
    }

}
