package com.example.administrator.jipinshop.jpush;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author 莫小婷
 * @create 2019/9/5
 * @Describe 设置别名
 */
public class JPushAlias {

    /**
     * 设置别名
     */
    public static void setAlias(Context context , String userid){
        JPushInterface.setAlias(context,userid,mTagsCallback);
    }


    private static final TagAliasCallback mTagsCallback = (code, alias, tags) -> {
        String logs;
        String TAG = "JPUSH";
        switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;

            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                break;

            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
        }
    };
}
