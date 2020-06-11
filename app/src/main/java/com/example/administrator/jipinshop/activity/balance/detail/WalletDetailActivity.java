package com.example.administrator.jipinshop.activity.balance.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWalletDetailBinding;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe 收益详情
 */
public class WalletDetailActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    WalletDetailPresenter mPresenter;

    private ActivityWalletDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_wallet_detail);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBaseActivityComponent.inject(this);
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
