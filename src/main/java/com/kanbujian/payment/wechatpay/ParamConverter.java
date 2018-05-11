package com.kanbujian.payment.wechatpay;

import com.kanbujian.payment.Converter;
import com.kanbujian.payment.wechatpay.lib.XmlUtil;

import java.util.Map;

public class ParamConverter implements Converter {
    @Override
    public Map convertNotifyParams(byte[] data) {
        return XmlUtil.dom2Map(new String(data));
    }
}
