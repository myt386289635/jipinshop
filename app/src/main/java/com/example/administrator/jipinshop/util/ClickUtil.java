package com.example.administrator.jipinshop.util;

/**
 * @author 莫小婷
 * @create 2018/8/27
 * @Describe 防止双重点击
 */
public class ClickUtil {

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
