package com.example.administrator.jipinshop.netwrok;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.auto.ApplicationScope;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.util.NetUtils;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

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
                addInterceptor(loggingInterceptor);
        if (sslSocketFactory != null){
            builder.sslSocketFactory(sslSocketFactory);
        }
        return builder;
    }


    @ApplicationScope
    @Provides
    OkHttpClient provideOkHttpClient(Context context ,OkHttpClient.Builder builder) {
        builder.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            //添加公共请求头
            Request authorised = originalRequest.newBuilder()
                    .addHeader("token", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))
                    .addHeader("uuid", new DeviceUuidFactory(context).getDeviceUuid())
                    .addHeader("client","android")
                    .addHeader("appVersion", SettingActivity.getVerName(context))
                    .addHeader("model",DeviceUuidFactory.getSystemModel())
                    .addHeader("sysVersion",DeviceUuidFactory.getSystemVersion())
                    .addHeader("netInfo", NetUtils.getNetworkState(context))
                    .addHeader("res", DistanceHelper.getAndroiodScreenwidthPixels(context) + " x " + DistanceHelper.getAndroiodScreenheightPixels(context))
                    .build();
            //添加公共请求参数
            long timestamp = System.currentTimeMillis();
            Map<String,String> map = new HashMap<>();
            if (authorised.method().equals("GET")) {
                HttpUrl url = authorised.url();
                HttpUrl.Builder builder1 = url.newBuilder()
                        .addEncodedQueryParameter("timestamp", timestamp+ "");
                if (!TextUtils.isEmpty(url.encodedQuery())){
                    String[] strings = url.encodedQuery().split("&");
                    for (int i = 0; i < strings.length; i++) {
                        String[] s = strings[i].split("=");
                        if (s.length == 1){
                            map.put(s[0],"");
                        }else {
                            map.put(s[0],s[1]);
                        }
                    }
                }
                map.put("timestamp",timestamp + "");
                if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    map.put("token",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token));
                    builder1 = builder1.addEncodedQueryParameter("token",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token));
                }
                builder1 = builder1.addEncodedQueryParameter("sign",createSign(map,false));
                HttpUrl newUrl = builder1.build();
                authorised = authorised.newBuilder()
                        .url(newUrl).build();
            }else if (authorised.method().equals("POST")){
                Request.Builder requestBuilder = authorised.newBuilder();
                if(authorised.body() instanceof FormBody){//目前接口都是这种
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oldFormBody = (FormBody) authorised.body();
                    for(int i = 0;i<oldFormBody.size();i++){
                        newFormBody.addEncoded(oldFormBody.encodedName(i),oldFormBody.encodedValue(i));
                        map.put(oldFormBody.encodedName(i),oldFormBody.encodedValue(i));
                    }
                    map.put("timestamp",timestamp + "");
                    if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                        map.put("token",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token));
                        newFormBody.add("token",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token));
                    }
                    newFormBody.add("sign",createSign(map,false));
                    newFormBody.add("timestamp",timestamp + "");
                    requestBuilder.method(authorised.method(), newFormBody.build());
                    authorised = requestBuilder.build();
                }else {
                    RequestBody body = authorised.body();// 得到请求体
                    Buffer buffer = new Buffer();// 创建缓存
                    body.writeTo(buffer);//将请求体内容,写入缓存
                    String parameterStr = buffer.readUtf8();// 读取参数字符串
//                    Log.d("moxiaoting", parameterStr + "---");
                    if (TextUtils.isEmpty(parameterStr)){//post没有请求体时
                        FormBody.Builder newFormBody = new FormBody.Builder();
                        map.put("timestamp",timestamp + "");
                        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                            map.put("token",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token));
                            newFormBody.add("token",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token));
                        }
                        newFormBody.add("sign",createSign(map,false));
                        newFormBody.add("timestamp",timestamp + "");
                        requestBuilder.method(authorised.method(), newFormBody.build());
                        authorised = requestBuilder.build();
                    }
                }
            }

            return chain.proceed(authorised);
        });
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);// 发送请求，获得回包
            // 对返回code统一拦截
            try {
                Charset charset;
                charset = Charset.forName("UTF-8");
                ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);//把内容copy了一边，所以不会有影响，peekBody() 方法返回的是一个新的 response 的 body
                Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
                BufferedReader reader = new BufferedReader(jsonReader);
                StringBuilder sbJson = new StringBuilder();
                String line = reader.readLine();
                do {
                    sbJson.append(line);
                    line = reader.readLine();
                } while (line != null);
//                Log.e("OKHttpModule", sbJson.toString());// 输出返回结果
//                SuccessBean successBean = new Gson().fromJson(sbJson.toString(),SuccessBean.class);
//                if(successBean.getCode() == 602){
//                    MyApplication.getInstance().startActivity(new Intent(MyApplication.getInstance(),LoginActivity.class));
//                    SPUtils.getInstance(CommonDate.USER).clear();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
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

    private String createSign(Map<String, String> params, boolean encode)
            throws UnsupportedEncodingException {
        params.put("accessKey","quality-shop");
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }
//        Log.d("OKHttpModule", temp.toString());
        return EncryptUtils.encryptMD5ToString(temp.toString()).toUpperCase();
    }

}
