package com.example.administrator.jipinshop.netwrok;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author 莫小婷
 * @create 2018/9/3
 * @Describe
 */
public class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
}
