package com.example.administrator.jipinshop.activity.sign;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.integral.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 签到页面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener {

    public static final String eventbusTag = "SignActivity";

    @Inject
    SignPresenter mPresenter;

    private ActivitySignBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
//        mPresenter.setSignView(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.sign_rule:
                //积分规则
                break;
            case R.id.sign_signed:

                break;
            case R.id.sign_detail:
                //积分明细
                startActivity(new Intent(this, IntegralDetailActivity.class));
                break;
        }
    }
}
