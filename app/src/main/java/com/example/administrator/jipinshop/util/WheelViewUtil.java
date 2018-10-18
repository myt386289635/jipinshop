package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.wheelview.TimePickerView;
import com.example.administrator.jipinshop.util.wheelview.WheelView;
import com.example.administrator.jipinshop.util.wheelview.adapter.ArrayWheelAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 莫小婷
 * @create 2018/10/9
 * @Describe 底部选择器
 */
public class WheelViewUtil {

    /**
     * 时间选择回调
     */
    public interface TimerPickerCallBack {
        void onTimeSelect(String date);
    }

    /**
     * 弹出时间选择
     *
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型
     * @param format   时间格式化
     * @param callBack 时间选择回调
     */
    public static void alertTimerPicker(Context context,String startDate , TimePickerView.Type type, final String format, final TimerPickerCallBack callBack) {
        TimePickerView pvTime = new TimePickerView(context, type);
        //控制时间范围
        //        Calendar calendar = Calendar.getInstance();
        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        if(startDate.contains("T")){
            startDate = startDate.substring(0,10);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            pvTime.setTime(simpleDateFormat.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(date -> {
//                        tvTime.setText(getTime(date));
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            callBack.onTimeSelect(sdf.format(date));
        });
        pvTime.setTextSize(16);
        //弹出时间选择器
        pvTime.show();
    }


    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int postion);
    }

    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(Context context, ArrayList<?> list, final OnWheelViewClick click) {

        final PopupWindow popupWindow = new PopupWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_wheel_option, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.btnSubmit);
        final WheelView wv_option = (WheelView) view.findViewById(R.id.wv_option);
        wv_option.setAdapter(new ArrayWheelAdapter(list));
        wv_option.setCyclic(false);
        wv_option.setTextSize(16);
        wv_option.setCurrentItem(0);
        tv_confirm.setOnClickListener(view1 -> {
            popupWindow.dismiss();
            click.onClick(view1, wv_option.getCurrentItem());
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(view12 -> {
            popupWindow.dismiss();
        });
        view.setOnTouchListener((view13, motionEvent) -> {
            int top = view13.findViewById(R.id.ll_container).getTop();
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                int y = (int) motionEvent.getY();
                if (y < top) {
                    popupWindow.dismiss();
                }
            }
            return true;
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }
}
