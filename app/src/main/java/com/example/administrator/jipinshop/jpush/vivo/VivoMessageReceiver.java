package com.example.administrator.jipinshop.jpush.vivo;

import android.content.Context;

import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * @author 莫小婷
 * @create 2020/7/27
 * @Describe vivo推送接收器
 */
public class VivoMessageReceiver extends OpenClientPushMessageReceiver {

    //当通知被点击时回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {

    }

    //当首次 turnOnPush 成功或 regId 发生改变时，回调此方法
    @Override
    public void onReceiveRegId(Context context, String s) {

    }
}
