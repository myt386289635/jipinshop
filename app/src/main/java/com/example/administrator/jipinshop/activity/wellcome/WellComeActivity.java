package com.example.administrator.jipinshop.activity.wellcome;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.wellcome.ad.SplashAdActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.eventbus.CommenBus;
import com.example.administrator.jipinshop.jpush.LoginUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.huawei.hms.push.HmsMessaging;
import com.meizu.cloud.pushsdk.PushManager;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 莫小婷
 * @create 2018/8/1
 * @Describe 欢迎页
 */
public class WellComeActivity extends BaseActivity {

    @Inject
    WellComePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        EventBus.getDefault().register(this);
        //预登陆获取手机号，为了一键登录
        LoginUtil.getPhone(this);
        //初始化阿里百川
        //按道理应该在application里设置，但是一旦出现无法跳转淘宝时，需从后台杀掉App，再重启才能跳转淘宝，这是阿里百川的bug
        //在启动页初始化，能避免用户不知手机后台是何物造成无法修复问题。只需关闭app再开启即可修复
        initAlibcTradeSDK();
        //初始化各个推送SDK
        initPush();
        //开始
        if(SPUtils.getInstance().getBoolean(CommonDate.FIRST,true)){
            //展示隐私协议dialog
            DialogUtil.servceDialog(this, v -> {
                permission();
            }, v -> {
                //关闭App
                finish();
            });
        }else {
            permission();
        }
    }

    private void initPush() {
        String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
        if (deviceBrand.equals("huawei")){
            JPushInterface.stopPush(this);//极光停止推送
            HmsMessaging hmsMessaging = HmsMessaging.getInstance(this);
            hmsMessaging.setAutoInitEnabled(true);
            notifyChannel(MyApplication.getInstance());//华为设置通知通道 兼容Android8.0及以上机型
        }else if (deviceBrand.equals("xiaomi")){
            JPushInterface.stopPush(this);//极光停止推送
            MiPushClient.registerPush(MyApplication.getInstance(), "2882303761517901504", "5721790192504");
            if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"").trim())){
                MiPushClient.setAlias(MyApplication.getInstance(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,""),null);
            }
        }else if (deviceBrand.equals("vivo")){
            PushClient.getInstance(MyApplication.getInstance()).initialize();
            PushClient.getInstance(MyApplication.getInstance()).turnOnPush(state -> {});
            if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"").trim())){
                PushClient.getInstance(MyApplication.getInstance()).
                        bindAlias(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId, ""), i -> {});
            }
        }else if (deviceBrand.equals("oppo")){
            HeytapPushManager.init(MyApplication.getInstance(),true);
            if (HeytapPushManager.isSupportPush()){
                JPushInterface.stopPush(this);//极光停止推送
                HeytapPushManager.register(MyApplication.getInstance(), "ac4720cb3ae742679670d39262fcb748",
                        "27f45959fb504ab48dab29cd90efdcd4", new ICallBackResultService() {
                            //注册的结果,如果注册成功,registerID就是客户端的唯一身份标识
                            @Override
                            public void onRegister(int responseCode, String registerID) {
                                if (responseCode == 0){
                                    //注册成功  上传registerId给后台
                                    mPresenter.sendRegTokenToServer(registerID,WellComeActivity.this.bindToLifecycle());
                                }else {
                                    HeytapPushManager.getRegister();//注册失败进行重试
                                }
                            }
                            //反注册的结果
                            @Override
                            public void onUnRegister(int responseCode) {}
                            //获取设置推送时间的执行结果
                            @Override
                            public void onSetPushTime(int responseCode, String pushTime) {}
                            //获取当前的push状态返回,根据返回码判断当前的push状态,返回码具体含义可以参考[错误码]
                            @Override
                            public void onGetPushStatus(int responseCode,int status) {}
                            //获取当前通知栏状态，返回码具体含义可以参考[错误码]
                            @Override
                            public void onGetNotificationStatus(int responseCode,int status) {}
                        });
                notifyChannel(MyApplication.getInstance());//oppo设置通知通道 兼容Android8.0及以上机型
            }
        }else if (deviceBrand.equals("meizu")){
            JPushInterface.stopPush(this);//极光停止推送
            PushManager.register(this, "133208", "26040f18468d4c4d908125012aed5375");
        }
    }


    CountDownTimer timer = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.AD))){
                //跳转广告
                startActivity(new Intent(WellComeActivity.this, AdActivity.class));
                finish();
            }else {
               //跳转他们的广告
                startActivity(new Intent(WellComeActivity.this, SplashAdActivity.class));
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void permission(){
        HasPermissionsUtil.permission(this, new HasPermissionsUtil(){
            @Override
            public void hasPermissionsSuccess() {
                super.hasPermissionsSuccess();
                if (timer != null) {
                    timer.start();
                }
            }

            @Override
            public void hasPermissionsFaile() {
                super.hasPermissionsFaile();
                if (timer != null) {
                    timer.start();
                }
            }

            @Override
            public void rePermissionsFaile() {
                super.rePermissionsFaile();
                if (timer != null) {
                    timer.start();
                }
            }

            @Override
            public void settingPermissions() {
                super.settingPermissions();
                if (timer != null) {
                    timer.start();
                }
            }

        }, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /***
     * 初始化阿里百川sdk
     */
    private void initAlibcTradeSDK() {
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(MyApplication.getInstance(), new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Log.e("AlibcTradeSDK", "初始化阿里百e川成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.e("AlibcTradeSDK", "初始化阿里百川失败,错误码=" + code + " / 错误消息=" + msg);
            }
        });
    }

    //oppo 设置通知通道 兼容Android8.0及以上机型
    private static void notifyChannel(Application context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "jpc_sys";
            String channelName = "系统消息";
            String channelDescription = "系统消息";
            NotificationChannel mNotificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.setDescription(channelDescription);
            Uri mUri = Uri.parse("android.resource://com.example.administrator.jipinshop/raw/tone");
            mNotificationChannel.setSound(mUri,Notification.AUDIO_ATTRIBUTES_DEFAULT);
            ((NotificationManager)context.getSystemService(Activity.NOTIFICATION_SERVICE)).createNotificationChannel(mNotificationChannel);
        }
    }

    @Subscribe
    public void finish(CommenBus bus){
        if (bus != null && bus.getTag().equals(SplashAdActivity.finish)){
            finish();
        }
    }
}
