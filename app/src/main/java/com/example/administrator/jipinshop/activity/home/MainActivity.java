package com.example.administrator.jipinshop.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

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
import com.example.administrator.jipinshop.fragment.tryout.TryFragment;
import com.example.administrator.jipinshop.util.InputMethodManagerLeak;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
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

public class MainActivity extends RxAppCompatActivity implements MainView {

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
    private TryFragment mTryFragment;

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
        mTryFragment = new TryFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mHomeFragment);
        mFragments.add(mFindFragment);
        mFragments.add(mEvaluationFragment);
//        mFragments.add(mTryFragment);
        mFragments.add(mMineFragment);

        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());
        mHomeAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mHomeAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);

        mPresenter.initTabLayout(this, mTabLayout);

        //版本更新
        mPresenter.getAppVersion(this.bindToLifecycle());

        View tabView = (View) mTabLayout.getTabAt(mFragments.size() - 1).getCustomView().getParent();
        tabView.setOnClickListener(v -> {
            if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,"").trim())){
                startActivityForResult(new Intent(this, LoginActivity.class),100);
            }
        });

//        DistanceHelper.getAndroiodScreenProperty(this);
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
            ToastUtil.show("再按一次退出程序");
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
    public void onSuccess(AppVersionbean versionbean) {
        if(versionbean.getData().getVersionCode() > UpDataUtil.getPackageVersionCode()){
            if(versionbean.getData().getNeedUpdate() == 0){
                //可以取消
                UpDataUtil.newInstance().downloadApk(this,false,versionbean.getData().getContent(),versionbean.getData().getDownloadUrl());//第一版
            }else {
                //必须强制更新
                UpDataUtil.newInstance().downloadApk(this,true,versionbean.getData().getContent(),versionbean.getData().getDownloadUrl());//第一版
            }

        }
    }
}
