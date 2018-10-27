package com.example.administrator.jipinshop.fragment.home.health;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.HealthFragmentGridAdapter;
import com.example.administrator.jipinshop.adapter.HealthFragmentRecyclerAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.HealthFragmentBean;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.databinding.FragmentHealthBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 个户健康
 */
public class HealthFragment extends DBBaseFragment implements HealthFragmentGridAdapter.OnItem, OnRefreshListener, HealthFragmentRecyclerAdapter.OnItem, HealthFragmentView {

    @Inject
    HealthFragmentPresenter mPresenter;

    private FragmentHealthBinding mBinding;
    private List<HealthFragmentGridBean> gridViewList;
    private HealthFragmentGridAdapter mAdapter;

    private HealthFragmentRecyclerAdapter mRecyclerAdapter;
    private List<HealthFragmentBean.ListBean> recyclerList;

    private Boolean once = true;

    private int position = 0;//二级导航点击的位置   默认为第0个

    public static HealthFragment getInstance() {
        HealthFragment fragment = new HealthFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_health, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        gridViewList = new ArrayList<>();
        mAdapter = new HealthFragmentGridAdapter(gridViewList, getContext());
        mAdapter.setOnItem(this);
        mBinding.gridView.setAdapter(mAdapter);
        mBinding.gridView.setVisibility(View.GONE);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList = new ArrayList<>();
        mRecyclerAdapter = new HealthFragmentRecyclerAdapter(recyclerList, getContext());
        mRecyclerAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mRecyclerAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
    }

    /**
     * 二级导航点击事件
     */
    @Override
    public void onItem(int pos) {
        position = pos;
        mPresenter.refreshGirdView(getContext(), mBinding.swipeToLoad, gridViewList, pos, mAdapter, mBinding.recyclerView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            if (gridViewList.size() != 0) {
                if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.HealthFragmentDATA,""))) {
                    initDate();
                }
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
        if(gridViewList.size() != 0){
            mPresenter.goodRank(gridViewList.get(position).getCategoryid(),this.bindToLifecycle());
        }else {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "网络请求错误,请重新开启app", Toast.LENGTH_SHORT).show();
        }
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
                for (int i = 0; i < tabBean.getList().get(1).getSubtilte().size(); i++) {
                    if (i == 0) {
                        gridViewList.add(new HealthFragmentGridBean(tabBean.getList().get(1).getSubtilte().get(i).getCategoryname(), true,tabBean.getList().get(1).getSubtilte().get(i).getCategoryid()));
                    } else {
                        gridViewList.add(new HealthFragmentGridBean(tabBean.getList().get(1).getSubtilte().get(i).getCategoryname(), false,tabBean.getList().get(1).getSubtilte().get(i).getCategoryid()));
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else {
                mBinding.gridView.setVisibility(View.GONE);
            }
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }


    public void stopResher(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    @Override
    public void onSuccess(HealthFragmentBean healthFragmentBean) {
        if(position == 0){
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.HealthFragmentDATA,new Gson().toJson(healthFragmentBean));
        }
        stopResher();
        if(healthFragmentBean.getList() != null  && healthFragmentBean.getList().size() != 0){
            recyclerList.clear();
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            recyclerList.addAll(healthFragmentBean.getList());
            mRecyclerAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
            mBinding.recyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFile(String error) {
        stopResher();
        if(position == 0 && !TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.HealthFragmentDATA,""))){
            initDate();
        }else {
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化本地数据
     */
    public void initDate(){
        HealthFragmentBean healthFragmentBean = new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.HealthFragmentDATA),HealthFragmentBean.class);
        recyclerList.clear();
        mBinding.inClude.qsNet.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        recyclerList.addAll(healthFragmentBean.getList());
        mRecyclerAdapter.notifyDataSetChanged();
    }
}
