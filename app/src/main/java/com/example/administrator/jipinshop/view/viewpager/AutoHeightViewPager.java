package com.example.administrator.jipinshop.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author 莫小婷
 * @create 2019/2/25
 * @Describe 自适应高度viewpager
 */
public class AutoHeightViewPager extends ViewPager {

    float startX = 0;
    float startY = 0;
    float xDistance = 0;
    float yDistance = 0;
    private int widthMeasureSpec;
    private int heightMeasureSpec;

    private int mCurPosition;
    private int mHeight = 0;
    private static HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

    public AutoHeightViewPager(Context context) {
        super(context);
    }

    public AutoHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;

        if (mChildrenViews.size() > mCurPosition) {
            View child = mChildrenViews.get(mCurPosition);
            if (child != null) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                mHeight = child.getMeasuredHeight();
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重新设置高度  在adapter里使用时需要调用这个方法，为了让viewpager重新计算高度
     */
    public void resetHeight(int current) {
        this.mCurPosition = current;
        measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //解决viewPager和viewPager的滑动影响
        //当滑动recyclerView时，告知父控件不要拦截事件，交给子view处理
//        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        final float curX = ev.getX();
        final float curY = ev.getY();

        xDistance += Math.abs(curX - startX);
        yDistance += Math.abs(curY - startY);

        if (xDistance >= yDistance) {
            //横向滑动
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            //垂直滑动
            this.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param view  fragment
     * @param position  一级导航的位置
     * @param set   二级导航的位置
     */
    public static void setViewPosition(View view, int position , int set) {
        if (set == 0){
            //说明是二级导航的第一个fragment，以第一个fragment的高度为准，因为第一个fragment高度最高
            //翻页不进行高度缩短，因为不好看
            mChildrenViews.put(position, view);
        }
    }
}
