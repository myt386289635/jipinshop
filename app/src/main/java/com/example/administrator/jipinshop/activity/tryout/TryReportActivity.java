package com.example.administrator.jipinshop.activity.tryout;

import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityTryReportBinding;
import com.example.administrator.jipinshop.fragment.tryout.hot.TryCommenFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/20
 * @Describe 查看全部试用报告
 */
public class TryReportActivity extends BaseActivity implements View.OnClickListener {

    private ActivityTryReportBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_try_report);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("全部报告");
        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mFragments.add(TryCommenFragment.getInstance("1"));
        mFragments.add(TryCommenFragment.getInstance("2"));
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        initTabLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    public void initTabLayout() {
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < mFragments.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            if (i == 0) {
                textView.setText("最新");
            } else if (i == 1) {
                textView.setText("热门");
            }
            mBinding.tabLayout.getTabAt(i).setCustomView(view);
            int a = (int) getResources().getDimension(R.dimen.x120);
            textLether.add(a);
        }
        mBinding.tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_FF3939));
        mBinding.tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        mBinding.tabLayout.post(() -> {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) mBinding.tabLayout.getChildAt(0);
            int totle = textLether.get(0) + textLether.get(1);
            int dp10 = (mBinding.tabLayout.getWidth() - totle) / 2;
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
}
