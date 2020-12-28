package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.sp.CommonDate;

/**
 * @author 莫小婷
 * @create 2019/10/22
 * @Describe 消息通知工具
 */
public class NotificationUtil {

    //判断该app是否打开了通知
    /**
     * 可以通过NotificationManagerCompat 中的 areNotificationsEnabled()来判断是否开启通知权限。NotificationManagerCompat 在 android.support.v4.app包中，是API 22.1.0 中加入的。而 areNotificationsEnabled()则是在 API 24.1.0之后加入的。
     * areNotificationsEnabled 只对 API 19 及以上版本有效，低于API 19 会一直返回true
     */
    public static boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;
    }

    //判断是否需要打开设置界面
    public static void OpenNotificationSetting(Context context , OnNextLitener listener) {
        if (!isNotificationEnabled(context)) {
            showSettingDialog(context, v -> {
                if (listener != null){
                    listener.onNext();
                }
            });
        }else {
            if (listener != null){
                listener.onNext();
            }
        }
        SPUtils.getInstance().put(CommonDate.FIRST,false);
    }

    //打开手机设置页面
    /**
     * 假设没有开启通知权限，点击之后就需要跳转到 APP的通知设置界面，对应的Action是：Settings.ACTION_APP_NOTIFICATION_SETTINGS, 这个Action是 API 26 后增加的
     * 如果在部分手机中无法精确的跳转到 APP对应的通知设置界面，那么我们就考虑直接跳转到 APP信息界面，对应的Action是：Settings.ACTION_APPLICATION_DETAILS_SETTINGS*/
    public static void gotoSet(Activity context,int requestCode) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivityForResult(intent,requestCode);
    }

    private static void showSettingDialog(Context context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_notification,null);
        TextView dialog_sure =  view.findViewById(R.id.dialog_sure);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        dialog_cancle.setOnClickListener(v -> {
            dialog.dismiss();
            listener.onClick(v);
        });
        dialog_sure.setOnClickListener(v -> {
            dialog.dismiss();
            gotoSet((Activity) context,401);
        });
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(view);
    }

    public interface OnNextLitener {
        void onNext();
    }

}
