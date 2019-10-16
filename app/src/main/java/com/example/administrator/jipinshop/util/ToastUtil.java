package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class ToastUtil {

    public static void show(String info){
        ToastUtils.setBgResource(-1);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(info);
    }

    public static void showTop(String info, Context context){
        int yOffset = (int) context.getResources().getDimension(R.dimen.y166);
        ToastUtils.setBgResource(R.drawable.bg_toast);
        ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, yOffset);
        ToastUtils.showShort(info);
    }
}
