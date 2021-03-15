package com.example.administrator.jipinshop.activity.home.food;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWithdrawDetailBinding;

/**
 * @author 莫小婷
 * @create 2020/11/26
 * @Describe 外卖集合（单开页）
 */
public class TakeOutActivity extends BaseActivity implements View.OnClickListener {

    private ActivityWithdrawDetailBinding mBinding;
    // 定义
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_withdraw_detail);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("外卖返现");
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction =  supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_fragment, TakeOutFragment.getInstance()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

}
