package com.kanbujian.payment.wechatpay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// in jar
public class Md5Util {
    private static MessageDigest md = null;;
    static  {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static String encode(String raw){
        md.update(raw.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
