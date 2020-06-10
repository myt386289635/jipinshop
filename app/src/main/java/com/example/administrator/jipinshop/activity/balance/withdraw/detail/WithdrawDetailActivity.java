package com.example.administrator.jipinshop.activity.balance.withdraw.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWithdrawDetailBinding;
import com.example.administrator.jipinshop.fragment.balance.withdraw.WithdrawDetailFragment;

/**
 * @author 莫小婷
 * @create 2020/6/10
 * @Describe 提现明细 （2020.6.10）
 */
public class WithdrawDetailActivity extends BaseActivity implements View.OnClickListener {

    // 定义
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ActivityWithdrawDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_withdraw_detail);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("提现明细");
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction =  supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_fragment, WithdrawDetailFragment.getInstance()).commit();
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
