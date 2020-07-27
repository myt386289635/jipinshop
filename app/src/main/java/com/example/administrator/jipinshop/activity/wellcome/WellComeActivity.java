package com.example.administrator.jipinshop.activity.wellcome;

import android.Manifest;
import android.content.Intent;
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
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.wellcome.index.IndexMixActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.jpush.LoginUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.huawei.hms.push.HmsMessaging;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 莫小婷
 * @create 2018/8/1
 * @Describe 欢迎页
 */
public class WellComeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        if (timer != null) {
            timer.start();
        }
        //预登陆获取手机号，为了一键登录
        LoginUtil.getPhone(this);
        //初始化阿里百川
        //按道理应该在application里设置，但是一旦出现无法跳转淘宝时，需从后台杀掉App，再重启才能跳转淘宝，这是阿里百川的bug
        //在启动页初始化，能避免用户不知手机后台是何物造成无法修复问题。只需关闭app再开启即可修复
        initAlibcTradeSDK();
        //初始化各个推送SDK
        initPush();
    }

    private void initPush() {
        String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
        if (deviceBrand.equals("huawei")){
            JPushInterface.stopPush(this);//极光停止推送
            HmsMessaging hmsMessaging = HmsMessaging.getInstance(this);
            hmsMessaging.setAutoInitEnabled(true);
        }else if (deviceBrand.equals("xiaomi")){
            JPushInterface.stopPush(this);//极光停止推送
            MiPushClient.registerPush(MyApplication.getInstance(), "2882303761517901504", "5721790192504");
            if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"").trim())){
                ///登陆
                MiPushClient.setAlias(MyApplication.getInstance(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,""),null);
            }
        }
    }


    CountDownTimer timer = new CountDownTimer(1500, 500) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if(SPUtils.getInstance().getBoolean(CommonDate.FIRST,true)){
                //跳转到引导页
                startActivity(new Intent(WellComeActivity.this, IndexMixActivity.class));
                finish();
            }else {
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.AD))){
                    //跳转广告
                    startActivity(new Intent(WellComeActivity.this, AdActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(WellComeActivity.this, MainActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
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

        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA , Manifest.permission.READ_PHONE_STATE);
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
}
