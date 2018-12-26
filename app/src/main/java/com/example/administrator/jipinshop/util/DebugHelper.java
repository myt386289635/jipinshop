package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 莫小婷
 * @create 2018/8/20
 * @Describe
 */
public class DebugHelper {
    private static Boolean isDebug = null;

    /*用来控制内存检测 不进行内存检测**/
    public static boolean isDebug() {
        return false;
    }

    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            Log.e("DebugHelper", isDebug + ": isdebug");
//            Toast.makeText(context, isDebug + ": isdebug", Toast.LENGTH_SHORT).show();
        }
    }

    /*用来控制bugly上传的位置：是开发版本还是线上版本**/
    public static boolean getDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }
}
