package com.example.administrator.jipinshop.netwrok;

import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.example.administrator.jipinshop.auto.ApplicationScope;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OKHttpModule {

    public static final int CONNECT_TIME_OUT = 30;
    public static final int WRITE_TIME_OUT = 30;
    public static final int READ_TIME_OUT = 30;

    @ApplicationScope
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(SSLSocketFactory sslSocketFactory) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).
                writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS).
                readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).
                retryOnConnectionFailure(true).
                sslSocketFactory(sslSocketFactory).
                addInterceptor(loggingInterceptor);
        return builder;
    }


    @ApplicationScope
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder) {
        builder.addInterceptor(chain -> {
            Request originalRequest = chain.request();
//            if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
//                return chain.proceed(originalRequest);
//            }
            long time = System.currentTimeMillis();
            String sign = "quality-shop" + time;
            Request authorised = originalRequest.newBuilder()
                    .addHeader("timestamp", time+"")//时间搓
                    .addHeader("sign", EncryptUtils.encryptMD5ToString(sign))//Md5加密字段
                    .addHeader("token", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))
                    .build();
            return chain.proceed(authorised);
        });
        return builder.build();
    }


    @ApplicationScope
    @Provides
    SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

}
