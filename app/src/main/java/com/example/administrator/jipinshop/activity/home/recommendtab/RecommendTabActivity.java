package com.example.administrator.jipinshop.activity.home.recommendtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.ArticleTabAdapter;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TitleBean;
import com.example.administrator.jipinshop.fragment.home.recommend.tabitem.TabCommenFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.MyRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe 热卖榜、轻奢榜、新品榜、性价比榜页面
 */
public class RecommendTabActivity extends BaseActivity implements ArticleTabAdapter.OnClickItem, ViewPager.OnPageChangeListener {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recycler_view)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<TitleBean> mTabBeans;
    private ArticleTabAdapter mTabAdapter;
    private int set = 0;// 记录上一个位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_tab);
        mButterKnife = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText(getIntent().getStringExtra("title"));

        mFragments = new ArrayList<>();
        mTabBeans = new ArrayList<>();
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))) {
            TabBean mTabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);
            for (int i = 0; i < mTabBean.getData().size(); i++) {
                if (i == 0){
                    mTabBeans.add(new TitleBean(mTabBean.getData().get(i).getCategoryName().replace("榜",""),true));
                }else {
                    mTabBeans.add(new TitleBean(mTabBean.getData().get(i).getCategoryName().replace("榜",""),false));
                }
                mFragments.add(TabCommenFragment.getInstance(i,getIntent().getStringExtra("orderbyType")));
            }
        }else {
            mTabBeans.add(new TitleBean("综合",true));
            mFragments.add(TabCommenFragment.getInstance(0,getIntent().getStringExtra("orderbyType")));
        }
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTabAdapter = new ArticleTabAdapter(mTabBeans,this);
        mTabAdapter.setOnClickItem(this);
        mRecyclerView.setAdapter(mTabAdapter);
        mViewPager.addOnPageChangeListener(this);
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
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
        mButterKnife.unbind();
        super.onDestroy();
    }

    @Override
    public void onClickItem(int pos) {
        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int des = 0;
        if((pos - 1) - firstPosition > 0){
            ////从左边到点击到右边
            des = mRecyclerView.getChildAt((pos - 1) - firstPosition).getLeft();
            mRecyclerView.smoothScrollBy(des,0);
        }else if(pos - 1 >= 0 && (pos - 1) - firstPosition <= 0){
            //从右边点击到左边
            mRecyclerView.smoothScrollToPosition(pos - 1);
        }
        mViewPager.setCurrentItem(pos,false);
        mTabBeans.get(set).setTag(false);
        mTabBeans.get(pos).setTag(true);
        mTabAdapter.notifyDataSetChanged();
        set = pos;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int des = 0;
        if((i - 1) - firstPosition > 0){
            ////从左边到点击到右边
            des = mRecyclerView.getChildAt((i - 1) - firstPosition).getLeft();
            mRecyclerView.smoothScrollBy(des,0);
        }else if(i - 1 >= 0 && (i - 1) - firstPosition <= 0){
            //从右边点击到左边
            mRecyclerView.smoothScrollToPosition(i - 1);
        }
        mTabBeans.get(set).setTag(false);
        mTabBeans.get(i).setTag(true);
        mTabAdapter.notifyDataSetChanged();

        set = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
