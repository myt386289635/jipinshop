package com.example.administrator.jipinshop.activity.wellcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.gson.Gson;

/**
 * @author 莫小婷
 * @create 2020/3/9
 * @Describe 广告页
 */
public class AdActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageView;
    private TextView mTextView;
    private EvaluationTabBean.DataBean.AdListBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        initView();
    }

    private void initView() {
        mImageView = findViewById(R.id.ad_image);
        mTextView = findViewById(R.id.ad_jump);
        if (timer != null) {
            timer.start();
        }
        mTextView.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.AD),EvaluationTabBean.DataBean.AdListBean.class);
        GlideApp.loderImage(this, mBean.getImg(),mImageView,0,0);
    }

    CountDownTimer timer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText("跳过"+(millisUntilFinished / 1000)+"s");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(AdActivity.this, MainActivity.class));
            finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ad_jump:
                if (timer != null) {
                    timer.cancel();
                }
                startActivity(new Intent(AdActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.ad_image:
                if (mBean.getType() != 0){
                    if (timer != null) {
                        timer.cancel();
                    }
                    finish();
                    startActivity(new Intent(AdActivity.this, MainActivity.class)
                            .putExtra("targetType",mBean.getType() + "")
                            .putExtra("target_id" , mBean.getObjectId())
                            .putExtra("target_title" , mBean.getName())
                            .putExtra("source" , mBean.getSource())
                            .putExtra("isAd",true)//从广告页点击过来的
                    );
                }
                break;
        }
    }
}
