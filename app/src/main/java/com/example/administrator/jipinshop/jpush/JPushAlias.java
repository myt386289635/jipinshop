package com.example.administrator.jipinshop.jpush;

import android.content.Context;

import cn.jpush.android.api.JPushInterface;

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
        JPushInterface.setAlias(context,1,userid);
    }

    /**
     * 删除别名
     */
    public static void deleteAlias(Context context){
        JPushInterface.deleteAlias(context,1);
    }
}
