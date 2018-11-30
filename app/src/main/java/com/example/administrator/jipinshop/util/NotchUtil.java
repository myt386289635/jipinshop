package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/29
 * @Describe 适配Android pie 刘海屏
 */
public class NotchUtil {

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void notch(Activity context) {
        try {
            View decorView = context.getWindow().getDecorView();
            decorView.post(() -> {
                try{
                    DisplayCutout cutout = decorView.getRootWindowInsets().getDisplayCutout();
                    List<Rect> rects = cutout.getBoundingRects();
                    if (rects == null || rects.size() == 0) {
                        Log.e("TAG", "不是刘海屏");
                    } else {
                        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                        context.getWindow().setAttributes(lp);
                    }
                }catch (Exception e){
                    //没有测试机测试  避免出错
                    Log.d("NotchUtil", e.getMessage());
                }
            });
        }catch (Exception e){
            //没有测试机测试  避免出错
            Log.d("NotchUtil", e.getMessage());
        }
    }

}
