package com.example.administrator.jipinshop.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.AppVersionbean;
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
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends RxAppCompatActivity implements ViewPager.OnPageChangeListener, MainView {

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
        mPresenter.setView(this);

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
        mPresenter.getAppVersion(this.bindToLifecycle());

        View tabView = (View) mTabLayout.getTabAt(3).getCustomView().getParent();
        tabView.setOnClickListener(v -> {
            if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId).trim())){
                startActivityForResult(new Intent(this, LoginActivity.class),100);
            }
        });
        //解决因为没有缓存fragment造成的各种问题，经验来说最好设置viewPager.setOffscreenPageLimit(3);
        // 但是这样会导致引导页打开首页时过慢，因为要预加载其余三个页面的UI（onCreateView）
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
        if(i == 0){
            if(mHomeFragment != null && mHomeFragment.getView() != null){
                mHomeFragment.getView().requestLayout();
            }
        }else if(i == 2){
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

    @Override
    public void onSuccess(AppVersionbean versionbean) {
        if(versionbean.getAppVersion().getVersionCode() > UpDataUtil.getPackageVersionCode()){
            if(versionbean.getAppVersion().getNeedUpdate() == 0){
                //可以取消
                UpDataUtil.newInstance().downloadApk(this,false,versionbean.getAppVersion().getContent(),versionbean.getAppVersion().getDownloadUrl());//第一版
            }else {
                //必须强制更新
                UpDataUtil.newInstance().downloadApk(this,true,versionbean.getAppVersion().getContent(),versionbean.getAppVersion().getDownloadUrl());//第一版
            }

        }
    }
}
