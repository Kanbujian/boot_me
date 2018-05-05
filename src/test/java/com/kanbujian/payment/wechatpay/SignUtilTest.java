package com.kanbujian.payment.wechatpay;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SignUtilTest {
    Map params = null;
    String appSecret = "";

    @Test
    void sign() {
        params = new HashMap();
        params.put("appid", "wxd930ea5d5a258f4f");
        params.put("mch_id", "10000100");
        params.put("device_info", "1000");
        params.put("body", "test");
        params.put("nonce_str", "ibuaiVcKdpRxkhJA");
        appSecret = "192006250b4c09247ec02edce69f6a2d";


        String expected = "9A0A8659F005D6984697E2CA0A9CF3B7";
        String result = SignUtil.sign(params, appSecret);
        assertEquals(expected, result);
    }

}