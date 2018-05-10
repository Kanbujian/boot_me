package com.kanbujian.payment.wechatpay.lib;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// in jar
public class SignUtil {

    public static String sign(Map params, String secret){
        Stream<Map.Entry<String, Object>> stream= params.entrySet().stream();
        String signTemp = stream.filter(entry -> (entry.getValue()!=null && !"".equals(entry.getValue())))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey().concat("=").concat(""+ entry.getValue()))
                .collect(Collectors.joining("&"));
        signTemp = signTemp.concat(String.format("&key=%s", secret));
        return Md5Util.encode(signTemp).toUpperCase();
    }

    public static Long timeStamp(){
        return new Date().getTime();
    }

    public static String nonceStr(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String toXml(Map params){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>\\n");
        params.forEach((k, v) ->{
            sb.append(String.format("<%s>%s</%s>", k, cdata(v), k));
        });
        sb.append("</xml>");
        return sb.toString();
    }

    public static Object cdata(Object value){
        if (value instanceof Integer){
            return value;
        }else{
            return String.format("<![CDATA[%s]]>", value);
        }
    }


    public static boolean isValid(Map params, String secret){
        String sign = (String) params.remove("sign");
        return sign.equals(sign(params, secret));
    }
}
