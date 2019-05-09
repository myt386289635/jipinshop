package com.example.administrator.jipinshop.activity.wellcome;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.StartPageBean;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/1
 * @Describe 欢迎页
 */
public class WellComeActivity extends BaseActivity implements WellComeView {

    private ImageView mImageView;
    @Inject
    WellComePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
//        mPresenter.getStartupImgs(this.bindToLifecycle());
        mImageView = findViewById(R.id.well_image);
//        if (TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.startPage,""))){
            mImageView.setImageResource(R.mipmap.start_page);
//        }else {
//            GlideApp.loderImage(this,SPUtils.getInstance().getString(CommonDate.startPage),mImageView,0,0);
//        }
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

    @Override
    public void onSuccess(StartPageBean startPageBean) {
//        SPUtils.getInstance().put(CommonDate.startPage,startPageBean.get);
    }
}
