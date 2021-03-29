package com.example.administrator.jipinshop.activity.wellcome.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.bean.eventbus.CommenBus;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.gyf.barlibrary.ImmersionBar;
import com.qubian.mob.AdManager;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 莫小婷
 * @create 2021/3/24
 * @Describe 开屏广告
 */
public class SplashAdActivity extends Activity {

    public static final String finish = "finishPage";

    private FrameLayout mSplashContainer;
    private int error = 0;//失败次数
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_splash);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mSplashContainer = findViewById(R.id.home_fragment);
        //加载开屏广告
        error = 0;
        onAd();
    }

    public void onJump(){
        if(SPUtils.getInstance().getBoolean(CommonDate.FIRST,true)){
            startActivity(new Intent(SplashAdActivity.this, LoginActivity.class));
        }else {
            startActivity(new Intent(SplashAdActivity.this, MainActivity.class));
        }
        finish();
        EventBus.getDefault().post(new CommenBus(SplashAdActivity.finish));
    }

    public void onAd(){
        AdManager.loadSplashAd("1371288177377034333", "", "", this, mSplashContainer, new AdManager.SplashAdLoadListener() {
            @Override
            public void onAdFail(String s) {
                error++;
                if (error >= 2){
                    onJump();
                }else {
                    onAd();
                }
            }

            @Override
            public void onAdDismiss() {
                onJump();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                onJump();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
    }
}
