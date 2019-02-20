package com.example.administrator.jipinshop.activity.home.tabitem;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.fragment.home.commen.tabitem.ItemTabCommenFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe 榜单二级目录详情页面
 */
public class ItemTabActivity extends BaseActivity {

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
        setContentView(R.layout.activity_itemtab);
        mButterKnife = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText(getIntent().getStringExtra("title"));
        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        for (int i = 0; i < 5; i++) {
            mFragments.add(ItemTabCommenFragment.getInstance(i));
        }
        mAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
        initTabLayout();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null != view && view instanceof RelativeLayout) {
                    ((TextView) ((RelativeLayout) view).getChildAt(0)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof RelativeLayout) {
                    ((TextView) ((RelativeLayout) view).getChildAt(0)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            if (i == 0) {
                textView.setText("综合榜");
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else if (i == 1){
                textView.setText("热卖榜");
            }else if (i == 2){
                textView.setText("轻奢榜");
            }else if (i == 3){
                textView.setText("新品榜");
            }else {
                textView.setText("性价比榜");
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
            int totle = textLether.get(0) + textLether.get(1) + textLether.get(2) + textLether.get(3)+ textLether.get(4);
            int dp10 = (mTabLayout.getWidth() - totle) / mFragments.size();
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
