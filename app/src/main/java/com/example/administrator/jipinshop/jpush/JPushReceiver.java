package com.example.administrator.jipinshop.jpush;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.bean.JPushBean;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author 莫小婷
 * @create 2018/8/14
 * @Describe 接受极光自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {

    public static final String TAG = "JPushReceiver";
    private static final String PUSH_CHANNEL_ID = "JPush";//android8.0需要，否则通知出不来

    private NotificationManager nm;

    @Inject
    AppStatisticalUtil mStatisticalUtil;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        ApplicationComponent mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(context))
                        .build();
        mApplicationComponent.inject(this);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.e(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "接受到推送下来的自定义消息");
            if (bundle != null){
                receivingNotification(context, bundle);
            }
            EventBus.getDefault().post(JPushReceiver.TAG);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "接受到推送下来的通知");
            EventBus.getDefault().post(JPushReceiver.TAG);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "用户点击打开了通知");
            if (bundle != null){
                String newsInfo = bundle.getString(JPushInterface.EXTRA_EXTRA);
                JPushBean jPushBean = new Gson().fromJson(newsInfo, JPushBean.class);
//              Log.e(TAG,newsInfo);
                if (jPushBean != null){
                    String targetType = jPushBean.getTargetType();
                    String target_id = jPushBean.getTargetId();
                    String target_title = jPushBean.getTargetTitle();
                    String source= jPushBean.getSource();
                    String remark = jPushBean.getRemark();
                    String messageId = jPushBean.getJpcMsgId();
                    mStatisticalUtil.addEvent("push_click." + messageId);
                    if (isExistMainActivity(context)){//是否已经启动MainActivity
                        ShopJumpUtil.openBanner(context, targetType, target_id, target_title,source,remark);
                    }else {
                        //启动APP的代码
                        context.startActivity(new Intent(context, MainActivity.class)
                                .putExtra("targetType",targetType)
                                .putExtra("target_id" , target_id)
                                .putExtra("target_title" , target_title)
                                .putExtra("source" , source)
                                .putExtra("remark",remark)
                                .putExtra("isAd",true)//从广告页点击过来的
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    }
                }
            }else {
                Intent intentDefult = new Intent();
                intentDefult.setClass(context, MainActivity.class);
                intentDefult.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentDefult);
            }
        } else {
            Log.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


    /**
     * 自定义通知栏
     * @param context
     * @param bundle
     */
    private void receivingNotification(Context context, Bundle bundle){
        MyApplication.getInstance().setNotificationNum( MyApplication.getInstance().getNotificationNum() + 1);
        if(MyApplication.getInstance().getNotificationNum() > 100) {
            MyApplication.getInstance().setNotificationNum(1);
        }
        Log.d("JPushReceiver", "mNotificationNum:" + MyApplication.getInstance().getNotificationNum());
        Intent intent = new Intent(context, MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, MyApplication.getInstance().getNotificationNum(), intent, 0);

        String title = "";
        if(bundle.getString(JPushInterface.EXTRA_TITLE,null) == null){
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE,"");
        }else {
            title = bundle.getString(JPushInterface.EXTRA_TITLE,"");
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,PUSH_CHANNEL_ID)
                        .setTicker(bundle.getString(JPushInterface.EXTRA_MESSAGE,""))
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle(title)
                        .setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE,""))
                        .setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);//点击通知头自动取消
        //悬挂式通知是5.0以后才出现的。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
            mBuilder.setPriority(Notification.PRIORITY_HIGH);//设置最高权限
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 1,// requestCode是0的时候三星手机点击通知栏通知不起作用
            new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setFullScreenIntent(pendingIntent1,true);
        }
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);//设置铃声及震动效果等
        mNotifyMgr.notify(MyApplication.getInstance().getNotificationNum(), mBuilder.build());

    }


    private boolean isExistMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        boolean flag = false;
        if (cmpName != null) {// 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);//获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) {// 说明它已经启动了
                    flag = true;
                    break;//跳出循环，优化效率
                }
            }
        }
        return flag;//true 存在 falese 不存在
    }
}
