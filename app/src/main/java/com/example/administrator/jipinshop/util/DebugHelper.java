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
    public static boolean isDebug() {
//        return isDebug == null ? false : isDebug.booleanValue();
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
}
