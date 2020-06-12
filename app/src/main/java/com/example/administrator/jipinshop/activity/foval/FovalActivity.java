package com.example.administrator.jipinshop.activity.foval;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticleFragment;
import com.example.administrator.jipinshop.fragment.foval.find.FovalFindFragment;
import com.example.administrator.jipinshop.fragment.foval.goods.FovalGoodsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe 收藏
 */
public class FovalActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title_line)
    View mTitleLine;

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
        mTitleBack.setOnClickListener(v -> finish());
        mTitleTv.setText("我的收藏");
        mTitleLine.setVisibility(View.INVISIBLE);
        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mFragments.add(FovalGoodsFragment.getInstance());
        mFragments.add(FovalArticleFragment.getInstance());//问答
        mFragments.add(FovalFindFragment.getInstance("2"));//评测
        mAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
        initTabLayout();
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    public void initTabLayout() {
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < mFragments.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            if (i == 0) {
                textView.setText("商品");
            }else if (i == 1){
                textView.setText("问答");
            } else{
                textView.setText("评测");
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
            int totle = textLether.get(0) + textLether.get(1) + textLether.get(2);
            int dp10 = (mTabLayout.getWidth() - totle) / mFragments.size();
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);
                tabView.setPadding(0, 0, 0, 0);
                int width = textLether.get(i) + dp10;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10 / 2;
                params.rightMargin = dp10 / 2;
                tabView.setLayoutParams(params);
                tabView.invalidate();
            }
        });
    }
}
