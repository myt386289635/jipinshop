package com.example.administrator.jipinshop.view.viewpager.transformer;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe
 */
public class ScalePagerTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.0f;
    private static final float MAX_ROTATION = 10;
    private static final float MIN_SCALE = 0.9f;

    @Override
    public void transformPage(View page, float position) {

//        final float rotation = MAX_ROTATION * Math.abs(position);
//        if (position < -1) {
//            page.setRotationY(MAX_ROTATION);
//        }else if (position <= 0f) {
//            page.setRotationY(rotation);
//        }else if (position <= 1f){
//            page.setRotationY(-rotation);
//        }else { // (1,+Infinity]
//            // This page is way off-screen to the right.
//            page.setRotationY(-MAX_ROTATION);
//        }

        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }
        float tempScale = position < 0 ? 1 + position : 1 - position;
        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        // 一个公式
        float scaleValue = MIN_SCALE + tempScale * slope;
        page.setScaleX(scaleValue);
        page.setScaleY(scaleValue);
        if (Build.VERSION.SDK_INT < 19 && page.getParent() != null) {
            page.getParent().requestLayout();
        }
    }
}
