package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.blankj.utilcode.util.Utils;

public class DistanceHelper {

    private static float getDensity(){
        return Utils.getContext().getResources().getDisplayMetrics().density;
    }
    public static int dip2px(double dipValue) {
        return (int) (dipValue * getDensity() + 0.5f);
    }


    public static int getAndroiodScreenProperty(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.e("h_bl", "屏幕宽度（像素）：" + width);
        Log.e("h_bl", "屏幕高度（像素）：" + height);
        Log.e("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.e("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.e("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.e("h_bl", "屏幕高度（dp）：" + screenHeight);
        return screenWidth;
    }

    public static int getAndroiodScreenwidthPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）

        Log.e("h_bl", "屏幕宽度（像素）：" + width);
        return width;
    }


    public static int getAndroiodScreenheightPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;       // 屏幕高度（像素）
        Log.e("h_bl", "屏幕高度（像素）：" + height);
        return height;
    }
}
