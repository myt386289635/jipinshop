package com.example.administrator.jipinshop.jpush.huawei;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;

/**
 * @author 莫小婷
 * @create 2020/7/23
 * @Describe 华为推送
 */
public class MyHmsMessageService extends HmsMessageService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("MyHmsMessageService" , " token :" + token);
    }

    @Override
    public void onTokenError(Exception e) {
        super.onTokenError(e);
        Log.e("MyHmsMessageService", e.getMessage());
    }

}
