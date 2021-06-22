package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.jipinshop.bean.CitysBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe 时间选择器 用于选择 查看收益的    与生日选择器有区别  以后可以选择性使用
 */
public class PickerUtil {

    private TimePickerView pvNoLinkOptions;
    private OptionsPickerView mOptionsPickerBuilder;

    public void initDayTime(Activity activity,Calendar startDate,OnClickTime onClickTime){
        pvNoLinkOptions = new TimePickerBuilder(activity, (date, v) -> {
            onClickTime.onTime(getTime(date));
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

    public void initMonthTime(Activity activity,Calendar startDate,OnClickTime onClickTime){
        pvNoLinkOptions = new TimePickerBuilder(activity, (date, v) -> {
            onClickTime.onTime(getTime_Month(date));
        })
                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
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
                .setLabel("年", "月", "", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
    }

    //选择生日
    public void initBirth(Activity activity,String selectDate,OnClickTime onClickTime){
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900,1,1);
        if (!TextUtils.isEmpty(selectDate) && stringToDate(selectDate) != null){
            selectedDate.setTime(stringToDate(selectDate));
        }
        pvNoLinkOptions = new TimePickerBuilder(activity, (date, v) -> {
            onClickTime.onTime(getTime(date));
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(13)
                .setContentTextSize(12)
                .setTitleSize(13)//标题文字大小
                .setTitleText("选择生日")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setLineSpacingMultiplier(2.5f)
                .isAlphaGradient(true)
                .setTitleColor(0xff202020)//标题文字颜色
                .setSubmitColor(0xff4A90E2)//确定按钮文字颜色
                .setCancelColor(0xff4A90E2)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRangDate(startDate, Calendar.getInstance())//起始终止年月日设定
                .setDate(selectedDate)
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
    }

    //性别选择器
    public void initSex(Activity activity, OnClickTime onItem){
        List<String> list = new ArrayList<>();
        list.add("女");
        list.add("男");
        mOptionsPickerBuilder = new OptionsPickerBuilder(activity, (options1, options2, options3, v) -> {
            onItem.onTime(list.get(options1));
        })
                .setItemVisibleCount(5)
                .setTitleColor(0xff202020)//标题文字颜色
                .setSubmitColor(0xff4A90E2)//确定按钮文字颜色
                .setCancelColor(0xff4A90E2)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("性别")//标题
                .setSubCalSize(13)//确定和取消文字大小
                .setTitleSize(13)//标题文字大小
                .setContentTextSize(12)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .setLineSpacingMultiplier(2.5f)
                .setOutSideCancelable(true)
                .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        mOptionsPickerBuilder.setPicker(list);
        mOptionsPickerBuilder.setSelectOptions(0);
    }

    //地址选择器
    public void initAddress(Activity activity, List<CitysBean> province ,
                            List<List<String>> city, List<List<List<String>>> area,
                            OnClickTime onItem){
        mOptionsPickerBuilder = new OptionsPickerBuilder(activity, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String cityTxt;
            if (province.get(options1).getPickerViewText().equals(city.get(options1).get(options2))) {
                cityTxt = province.get(options1).getPickerViewText() + "-" + area.get(options1).get(options2).get(options3);
            } else {
                cityTxt = province.get(options1).getPickerViewText() + "-" +
                        city.get(options1).get(options2) + "-" +
                        area.get(options1).get(options2).get(options3);
            }
            onItem.onTime(cityTxt);
        })
                .setTitleText("请选择城市")
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setSubmitColor(0xff4A90E2)//确定按钮文字颜色
                .setCancelColor(0xff4A90E2)//取消按钮文字颜色
                .setTitleColor(0xff202020)//标题文字颜色
                .setSubCalSize(15)//确定和取消文字大小
                .setTitleSize(15)//标题文字大小
                .setContentTextSize(20)//滚轮文字大小
                .build();
        mOptionsPickerBuilder.setPicker(province, city, area);//三级选择器
    }


    public void showPiker(){
        if (pvNoLinkOptions != null)
            pvNoLinkOptions.show();
    }

    public void showTextPiker(){
        if (mOptionsPickerBuilder != null)
            mOptionsPickerBuilder.show();
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTime_Month(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    public static Date stringToDate(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public interface OnClickTime{
        void onTime(String date);
    }

}
