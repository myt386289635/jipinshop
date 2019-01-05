package com.example.administrator.jipinshop.util;

import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class ToastUtil {

    public static void show(String info){
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(info);
    }
}
