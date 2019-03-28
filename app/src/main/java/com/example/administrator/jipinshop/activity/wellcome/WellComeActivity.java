package com.example.administrator.jipinshop.activity.wellcome;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.gyf.barlibrary.ImmersionBar;

/**
 * @author 莫小婷
 * @create 2018/8/1
 * @Describe
 */
public class WellComeActivity extends AppCompatActivity{

    private ImmersionBar mImmersionBar;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();

        mImageView = findViewById(R.id.well_imageTag);
        mImageView.setVisibility(View.GONE);

        permission();
    }


    CountDownTimer timer = new CountDownTimer(2 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if(SPUtils.getInstance().getBoolean(CommonDate.FIRST,true)){
                //跳转到引导页
                startActivity(new Intent(WellComeActivity.this, IndexActivity.class));
                finish();
            }else {
                startActivity(new Intent(WellComeActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
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

        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }
}
