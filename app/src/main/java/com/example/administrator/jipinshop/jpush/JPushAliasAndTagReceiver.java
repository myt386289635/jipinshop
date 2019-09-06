package com.example.administrator.jipinshop.jpush;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @author 莫小婷
 * @create 2019/9/6
 * @Describe
 */
public class JPushAliasAndTagReceiver extends JPushMessageReceiver {

    String logs;
    String TAG = "JPUSH";

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        switch (jPushMessage.getErrorCode()) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                break;
            default:
                logs = "Failed with errorCode = " + jPushMessage.getErrorCode();
                Log.e(TAG, logs);
        }
    }

}
