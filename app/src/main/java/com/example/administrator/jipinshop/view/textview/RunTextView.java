package com.example.administrator.jipinshop.view.textview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author 莫小婷
 * @create 2020/11/3
 * @Describe 仿支付宝数字滚动效果
 */
public class RunTextView extends AppCompatTextView {
    //动画时长
    private int duration = 500;
    //显示数字
    private float number;
    //显示表达式
    private String regex;
    //显示表示式
    public static final String INTREGEX = "%1$01.0f";//不保留小数，整数
    public static final String FLOATREGEX = "%1$01.2f";//保留2位小数
    public static final String ONEREGEX = "%1$01.1f";//保留1位小数

    //获取当前数字
    public float getNumber() {
        return number;
    }

    //根据正则表达式，显示对应数字样式
    public void setNumber(float number) {
        this.number = number;
        setText(String.format(regex, number));
    }

    public RunTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 显示
     * @param number
     */
    public void runWithAnimation(float number, String regex, String time){
        if (TextUtils.isEmpty(regex)) {
            //默认为整数
            this.regex = INTREGEX;
        } else {
            this.regex = regex;
        }
        if (TextUtils.isEmpty(time)) {
            duration = 0;
        } else {
            duration = 500;
        }
        //修改number属性，会调用setNumber方法
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number", 0, number);
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }
}
