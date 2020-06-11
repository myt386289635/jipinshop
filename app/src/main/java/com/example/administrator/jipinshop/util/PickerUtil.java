package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.graphics.Color;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe 时间选择器 用于选择 查看收益的    与生日选择器有区别  以后可以选择性使用
 */
public class PickerUtil {

    private TimePickerView pvNoLinkOptions;

    public void initDayTime(Activity activity,Calendar startDate){
        pvNoLinkOptions = new TimePickerBuilder(activity, (date, v) -> {
           ToastUtil.show( getTime(date));
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(13)
                .setContentTextSize(12)
                .setTitleSize(13)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setLineSpacingMultiplier(2.5f)
                .isAlphaGradient(true)
                .setTitleColor(0xff202020)//标题文字颜色
                .setSubmitColor(0xff4A90E2)//确定按钮文字颜色
                .setCancelColor(0xff4A90E2)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRangDate(startDate,Calendar.getInstance())//起始终止年月日设定
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
    }

    public void showPiker(){
        if (pvNoLinkOptions != null)
            pvNoLinkOptions.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
