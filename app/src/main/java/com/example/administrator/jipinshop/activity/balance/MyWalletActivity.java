package com.example.administrator.jipinshop.activity.balance;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWalletBinding;
import com.example.administrator.jipinshop.fragment.balance.BudgetDetailFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe 我的钱包页面
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    MyWalletPresenter mPresenter;

    private ActivityWalletBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding  = DataBindingUtil.setContentView(this,R.layout.activity_wallet);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mPresenter.setStatusBarHight(mBinding.titleTop,mBinding.title,this);
//        mPresenter.initTitleLayout(mBinding.viewPager,mBinding.statusBar,mBinding.appbar,mBinding.titleContainer,mBinding.titleText);

        mBinding.viewPager.setNoScroll(true);
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mFragments = new ArrayList<>();
        mFragments.add(BudgetDetailFragment.getInstance());
        mFragments.add(BudgetDetailFragment.getInstance());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mPresenter.initTabLayout(this,mBinding.tabLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_record:
                //提现
                break;
            case R.id.title_totleText:
                //总资产解释
                break;
        }
    }

//    public AppBarLayout getBar(){
//        return mBinding.appbar;
//    }
}
