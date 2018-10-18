package com.example.administrator.jipinshop.fragment.home.electricity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.ElectricityFragmentGridAdapter;
import com.example.administrator.jipinshop.adapter.ElectricityFragmentRecyclerAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.databinding.FragmentElectricityBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 家用大电
 */
public class ElectricityFragment extends DBBaseFragment implements ElectricityFragmentGridAdapter.OnItem, OnRefreshListener, ElectricityFragmentRecyclerAdapter.OnItem {

    @Inject
    ElectricityFragmentPresenter mPresenter;

    private FragmentElectricityBinding mBinding;
    private List<HealthFragmentGridBean> gridViewList;
    private ElectricityFragmentGridAdapter mAdapter;

    private ElectricityFragmentRecyclerAdapter mRecyclerAdapter;
    private List<String> recyclerList;

    private Boolean once = true;

    public static ElectricityFragment getInstance() {
        ElectricityFragment fragment = new ElectricityFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_electricity, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);

        gridViewList = new ArrayList<>();
        mAdapter = new ElectricityFragmentGridAdapter(gridViewList, getContext());
        mAdapter.setOnItem(this);
        mBinding.gridView.setAdapter(mAdapter);
        mBinding.gridView.setVisibility(View.GONE);

        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList = new ArrayList<>();
        mRecyclerAdapter = new ElectricityFragmentRecyclerAdapter(recyclerList, getContext(), mPresenter);
        mRecyclerAdapter.setOnItem(this);
        mBinding.swipeTarget.setAdapter(mRecyclerAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
    }

    /**
     * 二级导航点击事件
     */
    @Override
    public void onItem(int pos) {
        mPresenter.refreshGirdView(getContext(), mBinding.swipeToLoad, gridViewList, pos, mAdapter, mBinding.swipeTarget);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            if (gridViewList.size() != 0) {
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            }
        }
    }

    /**
     * 页面刷新
     */
    @Override
    public void onRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        for (int i = 0; i < 10; i++) {
            recyclerList.add("");
        }
        mRecyclerAdapter.notifyDataSetChanged();
        // TODO: 2018/8/1 网络请求
    }


    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItemclick(int pos) {
        startActivity(new Intent(getContext(), ShoppingDetailActivity.class));
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initSubTab(String string) {
        if (string.equals(HomeFragment.subTab)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))) {
                mBinding.gridView.setVisibility(View.VISIBLE);
                TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);
                gridViewList.clear();
                for (int i = 0; i < tabBean.getList().get(4).getSubtilte().size(); i++) {
                    if (i == 0) {
                        gridViewList.add(new HealthFragmentGridBean(tabBean.getList().get(4).getSubtilte().get(i).getCategoryname(), true,tabBean.getList().get(4).getSubtilte().get(i).getCategoryid()));
                    } else {
                        gridViewList.add(new HealthFragmentGridBean(tabBean.getList().get(4).getSubtilte().get(i).getCategoryname(), false,tabBean.getList().get(4).getSubtilte().get(i).getCategoryid()));
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else {
                mBinding.gridView.setVisibility(View.GONE);
            }
        }
    }
}
