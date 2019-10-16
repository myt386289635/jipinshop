package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class ToastUtil {

    public static void show(String info){
        ToastUtils.setView(null);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(info);
    }

    public static void showTop(String info, Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(R.layout.toast, null);
        TextView toast_text = toastRoot.findViewById(R.id.toast_text);
        toast_text.setText(info);
        int yOffset = (int) context.getResources().getDimension(R.dimen.y166);
        ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, yOffset);
        ToastUtils.showCustomShort(toastRoot);
    }
}
