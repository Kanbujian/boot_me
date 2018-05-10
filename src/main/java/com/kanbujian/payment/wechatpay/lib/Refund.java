package com.kanbujian.payment.wechatpay.lib;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.Map;

public class Refund {
    private final String URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    private String appId;
    private String mchId;
    private String appSecret;
    private String certPath;
    private String certPwd;

    public Refund(String appId, String mchId, String appSecret, String certPath, String certPwd) {
        this.appId = appId;
        this.mchId = mchId;
        this.appSecret = appSecret;
        this.certPath = certPath;
        this.certPwd = certPwd;
    }

    public Map run(Map params) throws Exception {
        params.put("nonce_str", SignUtil.nonceStr());
        params.put("sign", SignUtil.sign(params, appSecret));

        byte[] certByteArray = trustedCertificatesInputStream(certPath);
        InputStream certInputStream = new ByteArrayInputStream(certByteArray);
        KeyStore keyStore = keyStore(certInputStream);
        X509TrustManager trustManager = trustManager(keyStore);
        SSLContext sslContext = sslContextForTrustedCertificates(keyStore, trustManager);

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                .build();

        RequestBody body = RequestBody.create(MediaType.parse("application/xml"), SignUtil.toXml(params));
        Request request = new Request.Builder().url(URL).post(body).build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
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

        certInputStream.close();

        return null;
    }

    private byte[] trustedCertificatesInputStream(String certPath) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request certRequest = new Request.Builder().url(certPath).build();
        Response certResponse = httpClient.newCall(certRequest).execute();
        if(certResponse.isSuccessful()){
            // InputStream io = certResponse.body().byteStream();
            byte[] certDate = certResponse.body().bytes();
            certResponse.body().close();
            return certDate;
        }else{
            throw new ResponseException("CAN_NOT_ACCESS_CERT", "can't access the cert", "证书地址错误或者没有权限");
        }
    }

    private KeyStore keyStore(InputStream instream) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try {
            keyStore.load(instream, certPwd.toCharArray());
        } finally {
            instream.close();
        }
        return keyStore;
    }

    private SSLContext sslContextForTrustedCertificates(KeyStore keyStore, TrustManager trustManager) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(keyManagers(keyStore), new TrustManager[]{ trustManager }, new SecureRandom());

        return sslContext;
    }

    private X509TrustManager trustManager(KeyStore keyStore) throws Exception {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyManager[] keyManagers(KeyStore keyStore) throws Exception {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, certPwd.toCharArray());
        return keyManagerFactory.getKeyManagers();
    }

    private void writeInputSteamToFile(InputStream io) throws IOException {
        byte[] data = new byte[io.available()];
        io.read(data);
        File f = new File("/ssss.p12");
        OutputStream os = new FileOutputStream(f);
        os.write(data);
        os.close();
    }

}
