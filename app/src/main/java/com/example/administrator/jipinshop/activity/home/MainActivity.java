package com.example.administrator.jipinshop.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.fragment.mine.MineFragment;
import com.example.administrator.jipinshop.util.InputMethodManagerLeak;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.UpDataUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.NoScrollViewPager;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;

    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private MineFragment mMineFragment;
    private EvaluationFragment mEvaluationFragment;

    private Unbinder mButterKnife;
    private ImmersionBar mImmersionBar;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButterKnife = ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        initView();
    }

    private void initView() {
        DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .build().inject(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();

        mViewPager.setNoScroll(true);
        mFragments = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mFindFragment = new FindFragment();
        mEvaluationFragment = new EvaluationFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mHomeFragment);
        mFragments.add(mFindFragment);
        mFragments.add(mEvaluationFragment);
        mFragments.add(mMineFragment);

        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());
        mHomeAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mHomeAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mPresenter.initTabLayout(this, mTabLayout);

        //版本更新
//        UpDataUtil.newInstance().downloadApk(this,"http://www.pgyer.com/app/installUpdate/8ce5d6ae9e32fb8a2eb270dbb22ce48b?sig=9vywFMfifTgXriKE4tQ%2Fz9wk%2FpLCz9pxo9gIVnZ9LClDWF7TL1ZWNpIz%2FhtQrfd%2B");//第一版

        View tabView = (View) mTabLayout.getTabAt(3).getCustomView().getParent();
        tabView.setOnClickListener(v -> {
            if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId).trim())){
                startActivityForResult(new Intent(this, LoginActivity.class),100);
            }
        });
        //主要是解决商品详情点击关注时无法刷新评测首页fragment的数据，不清楚原理是什么，但是这样解决能成功
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        InputMethodManagerLeak.fixInputMethodManagerLeak(null, this);
        mImmersionBar.destroy();
        mButterKnife.unbind();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case  400:
                TabLayout.Tab tab = mTabLayout.getTabAt(0);
                if (tab != null) {
                    tab.select();
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if(i == 2){
            if(mEvaluationFragment != null && mEvaluationFragment.getView() != null){
                mEvaluationFragment.getView().requestLayout();
            }
        }else if(i == 3){
            if(mMineFragment != null && mMineFragment.getView() != null){
                mMineFragment.getView().requestLayout();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
