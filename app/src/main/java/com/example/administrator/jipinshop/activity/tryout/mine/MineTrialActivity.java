package com.example.administrator.jipinshop.activity.tryout.mine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.fragment.foval.tryout.FovalTryFragment;
import com.example.administrator.jipinshop.fragment.tryout.mine.TrialCommonFragment;
import com.example.administrator.jipinshop.fragment.tryout.mine.TrialReportFragment;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/4/2
 * @Describe 我的试用页面
 */
public class MineTrialActivity extends BaseActivity {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foval);
        mButterKnife = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("我的试用");
        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mFragments.add(TrialCommonFragment.getInstance("0"));
        mFragments.add(TrialCommonFragment.getInstance("1"));
        mFragments.add(TrialCommonFragment.getInstance("-1"));
        mFragments.add(TrialReportFragment.getInstance());
        mAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
        initTabLayout();
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
        UMShareAPI.get(this).release();
    }

    public void initTabLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTabLayout.getLayoutParams();
        layoutParams.leftMargin = 0;
        layoutParams.rightMargin = 0;
        mTabLayout.setLayoutParams(layoutParams);
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < mFragments.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            if (i == 0) {
                textView.setText("申请中");
            } else if (i == 1){
                textView.setText("申请成功");
            }else if (i == 2){
                textView.setText("申请失败");
            }else {
                textView.setText("成功报告");
            }
            mTabLayout.getTabAt(i).setCustomView(view);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            textLether.add(a);
        }
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_FF3939));
        mTabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        mTabLayout.post(() -> {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) mTabLayout.getChildAt(0);
            int totle = textLether.get(0) + textLether.get(1) + textLether.get(2) + textLether.get(3);
            int dp10 = (mTabLayout.getWidth() - totle) / 4;
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);
                tabView.setPadding(0, 0, 0, 0);
                int width = textLether.get(i) + dp10;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10  / 2;
                params.rightMargin = dp10  / 2;
                tabView.setLayoutParams(params);
                tabView.invalidate();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
