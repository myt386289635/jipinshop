package com.example.administrator.jipinshop.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.bean.JPushBean;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

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


    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

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

            String newsInfo = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JPushBean jPushBean = new Gson().fromJson(newsInfo, JPushBean.class);
//            Log.e(TAG,newsInfo);
            if (jPushBean != null && !TextUtils.isEmpty(jPushBean.getTargetType())){
                openNotification(context,jPushBean);
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

    /**
     * 用户点击打开了通知
     * @param context
     * @param jPushBean
     */
    private void openNotification(Context context, JPushBean jPushBean){
        Intent intent = new Intent();
        switch (jPushBean.getTargetType()){
            case "11"://跳转到小分类榜单
                intent.setClass(context, ClassifyActivity.class);
                intent.putExtra("title",jPushBean.getTargetTitle() + "榜单");
                intent.putExtra("id",jPushBean.getTargetId());
                break;
            case "12"://跳转到商品详情
                intent.setClass(context,  ShoppingDetailActivity.class);
                intent.putExtra("goodsId",jPushBean.getTargetId());
                break;
            case "21"://测评文章
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",jPushBean.getTargetId());
                intent.putExtra("type","2");
                break;
            case "23"://清单详情web
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",jPushBean.getTargetId());
                intent.putExtra("type","7");
                break;
            case "24"://清单详情json
                intent.setClass(context, ReportDetailActivity.class);
                intent.putExtra("id",jPushBean.getTargetId());
                intent.putExtra("type","7");
                break;
            case "31"://试用商品详情(新品详情)
                intent.setClass(context,  TryDetailActivity.class);
                intent.putExtra("id",jPushBean.getTargetId());
                intent.putExtra("pos",-1);
                break;
            case "41"://免单详情
                intent.setClass(context,  FreeDetailActivity.class);
                intent.putExtra("id",jPushBean.getTargetId());
                break;
            default:
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    intent.setClass(context, LoginActivity.class);
                } else {
                    intent.setClass(context, MessageActivity.class);
                }
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
