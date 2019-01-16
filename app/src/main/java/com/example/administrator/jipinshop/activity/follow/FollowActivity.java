package com.example.administrator.jipinshop.activity.follow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityFollowBinding;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.fragment.follow.fans.FansFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 我的关注
 */
public class FollowActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    FollowPresenter mPresenter;
    private ActivityFollowBinding mBinding;

    private HomeFragmentAdapter mAdapter;
    private List<Fragment> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_follow);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mList.add(AttentionFragment.getInstance(getIntent().getIntExtra("page",0)));
        mList.add(FansFragment.getInstance(getIntent().getIntExtra("page",0)));
        mAdapter.setFragments(mList);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setCurrentItem(getIntent().getIntExtra("page",0));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mPresenter.initTabLayout(mList,this,mBinding.tabLayout);
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
