package com.example.administrator.jipinshop.view.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author 莫小婷
 * @create 2019/12/25
 * @Describe 为了轮播图而制作
 */
public class TouchViewPager extends ViewPager {

    private OnTouchListener mOnTouchListener;

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public TouchViewPager(@NonNull Context context) {
        super(context);
    }

    public TouchViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            if (mOnTouchListener != null)
                mOnTouchListener.startAutoPlay();
        } else if (action == MotionEvent.ACTION_DOWN) {
            if (mOnTouchListener != null)
                mOnTouchListener.stopAutoPlay();
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnTouchListener {
        void startAutoPlay();
        void stopAutoPlay();
    }
}
