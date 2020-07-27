package com.example.administrator.jipinshop.jpush.xiaomi;

import android.content.Context;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * @author 莫小婷
 * @create 2020/7/27
 * @Describe 小米推送接收器
 */
public class MiMessageReceiver extends PushMessageReceiver {

    //方法用来接收服务器向客户端发送的透传消息。
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
    }

    //方法用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发。
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
        Log.i("Mi--->Clicked:", miPushMessage.toString());
    }

    //方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        Log.i("Mi--->Arrived:", miPushMessage.toString());
    }

    //方法用来接收客户端向服务器发送注册命令后的响应结果。
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
        String command = miPushCommandMessage.getCommand();
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                Log.e("Mi", "Register push success.");
            } else {
                Log.e("Mi", "Register push fail.");
            }
        }
    }
}
