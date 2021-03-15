package com.example.administrator.jipinshop.activity.newpeople;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ActivityNewFreeBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;

import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/5/22
 * @Describe 新人免单（单开页）
 */
public class NewFreeActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    NewFreePresenter mPresenter;
    private ActivityNewFreeBinding mBinding;
    // 定义
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private NewFreeFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_free);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("新人免单专区");
        mBinding.inClude.titleLine.setVisibility(View.GONE);
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction =  supportFragmentManager.beginTransaction();
        mFragment = NewFreeFragment.getInstance();
        fragmentTransaction.add(R.id.home_fragment, mFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_rule:
                //新人免单规则
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.JP_H5_URL + "new-free/mdRule")
                        .putExtra(WebActivity.title,"活动规则")
                );
                break;
        }
    }

    public void onAd(List<MemberNewBean.DataBean.MessageListBean> messageListBeans){
        mPresenter.adFlipper(this,mBinding.viewFlipper,messageListBeans);
    }

}
