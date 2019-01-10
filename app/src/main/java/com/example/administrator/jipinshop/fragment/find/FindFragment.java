package com.example.administrator.jipinshop.fragment.find;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.adapter.ArticleTabAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.TitleBean;
import com.example.administrator.jipinshop.bean.eventbus.FindTabBus;
import com.example.administrator.jipinshop.databinding.FragmentFindBinding;
import com.example.administrator.jipinshop.fragment.find.common.CommonFindFragment;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FindFragment extends DBBaseFragment implements FindView, ArticleTabAdapter.OnClickItem, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String tag = "FindFragment2CommonFindFragment";

    @Inject
    FindFragmentPresenter mPresenter;

    private FragmentFindBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    private Boolean once = true;

    private List<TitleBean> mTabBeans;
    private ArticleTabAdapter mTabAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int set = 0;// 记录上一个位置

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            initTab(null);
            mPresenter.initTab(this.bindToLifecycle());
            once = false;
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_find,container,false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mPresenter.setView(this);

        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        mBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
        mTabBeans = new ArrayList<>();
        mTabAdapter = new ArticleTabAdapter(mTabBeans,getContext());
        mTabAdapter.setOnClickItem(this);
        mBinding.recyclerView.setAdapter(mTabAdapter);
        mBinding.viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onSucTab(EvaluationTabBean bean) {
        SPUtils.getInstance().put(CommonDate.FindTab,new Gson().toJson(bean));
        initTab(bean);
        EventBus.getDefault().post(new FindTabBus(FindFragment.tag));
    }

    @Override
    public void onFaile(String error) {
        EventBus.getDefault().post(new FindTabBus(FindFragment.tag));
        ToastUtil.show(error);
    }

    public void initTab(EvaluationTabBean tabBean) {
        mTabBeans.clear();
        mFragments.clear();
        if (tabBean != null) {
            if(tabBean.getData() != null && tabBean.getData().size() != 0){
                for (int i = 0; i < tabBean.getData().size(); i++) {
                    if(i == 0){
                        mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),true));
                    }else {
                        mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),false));
                    }
                    mFragments.add(CommonFindFragment.getInstance(i));
                }
            }
        } else {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.FindTab,""))){
                tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.FindTab),EvaluationTabBean.class);
                for (int i = 0; i < tabBean.getData().size(); i++) {
                    if(i == 0){
                        mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),true));
                    }else {
                        mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),false));
                    }
                    mFragments.add(CommonFindFragment.getInstance(i));
                }
            }else {
                mTabBeans.add(new TitleBean("精选",true));
                mTabBeans.add(new TitleBean("个护健康",false));
                mTabBeans.add(new TitleBean("厨房电器",false));
                mTabBeans.add(new TitleBean("生活电器",false));
                for (int i = 0; i < mTabBeans.size(); i++) {
                    mFragments.add(CommonFindFragment.getInstance(i));
                }
            }
        }
        mTabAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
    }

    @Override
    public void onClickItem(int pos) {
        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int des = 0;
        if((pos - 1) - firstPosition > 0){
            ////从左边到点击到右边
            des = mBinding.recyclerView.getChildAt((pos - 1) - firstPosition).getLeft();
            mBinding.recyclerView.smoothScrollBy(des,0);
        }else if(pos - 1 >= 0 && (pos - 1) - firstPosition <= 0){
            //从右边点击到左边
            mBinding.recyclerView.smoothScrollToPosition(pos - 1);
        }
        mBinding.viewPager.setCurrentItem(pos,false);
        mTabBeans.get(set).setTag(false);
        mTabBeans.get(pos).setTag(true);
        mTabAdapter.notifyDataSetChanged();
        set = pos;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int i) {
        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int des = 0;
        if((i - 1) - firstPosition > 0){
            ////从左边到点击到右边
            des = mBinding.recyclerView.getChildAt((i - 1) - firstPosition).getLeft();
            mBinding.recyclerView.smoothScrollBy(des,0);
        }else if(i - 1 >= 0 && (i - 1) - firstPosition <= 0){
            //从右边点击到左边
            mBinding.recyclerView.smoothScrollToPosition(i - 1);
        }
        mTabBeans.get(set).setTag(false);
        mTabBeans.get(i).setTag(true);
        mTabAdapter.notifyDataSetChanged();

        set = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    public AppBarLayout getBar(){
        return mBinding.appbar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_sreach:
                startActivity(new Intent(getContext(), SreachActivity.class));
                return;
        }
    }
}
