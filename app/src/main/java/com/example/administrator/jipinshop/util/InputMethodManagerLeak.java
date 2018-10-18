package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * @author 莫小婷
 * @create 2018/8/20
 * @Describe 防止InputMethodManager的内存溺出
 */
public class InputMethodManagerLeak {
    /**
     *去掉path to gc节点，防止InputMethodManager的内存溺出
     */
    public static void fixInputMethodManagerLeak(InputMethodManager mImm, Context destContext) {
        if (destContext == null) {
            return;
        }
        if(mImm == null){
            mImm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (mImm == null) {
            return;
        }
        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = mImm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(mImm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(mImm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}
