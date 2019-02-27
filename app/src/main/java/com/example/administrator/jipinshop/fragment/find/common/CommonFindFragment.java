package com.example.administrator.jipinshop.fragment.find.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommonFindAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.bean.eventbus.FindTabBus;
import com.example.administrator.jipinshop.databinding.FragmentFindCommonBinding;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 精选榜、个护健康、厨卫电器、生活家居、家用大电
 */
public class CommonFindFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, CommonFindView {

    @Inject
    CommonFindPresenter mPresenter;

    private FragmentFindCommonBinding mBinding;
    private List<FindListBean.DataBean> mList;
    private CommonFindAdapter mAdapter;

    private Boolean[] once = {true};
    private String id = "0";//数据id
    private int page = 1;
    private Boolean refersh = true;
    private List<EvaluationTabBean.DataBean.AdListBean> mAdListBeans;//轮播图

    public static CommonFindFragment getInstance(int position) {
        CommonFindFragment fragment = new CommonFindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once[0]){
            if(getArguments().getInt("type") != 0){
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_find_common,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdListBeans = new ArrayList<>();
        mAdapter = new CommonFindAdapter(mList, getContext(),true);
        mAdapter.setAdListBeans(mAdListBeans);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,((FindFragment)getParentFragment()).getBar(),once);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
    }

    //刷新
    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.getList(id,page + "",this.bindToLifecycle());
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initDate(FindTabBus findTabBus) {
        if (findTabBus != null && findTabBus.getTag().equals(FindFragment.tag)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.FindTab, ""))) {
                EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.FindTab), EvaluationTabBean.class);
                id = bean.getData().get(getArguments().getInt("type")).getCategoryId();
                mAdListBeans.addAll(bean.getData().get(getArguments().getInt("type")).getAdList());
            } else {
                id = "0";
            }
            if (getArguments().getInt("type") == 0) {
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    /**
     * 停止刷新
     */
    public void stopResher() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    /**
     * 停止加载
     */
    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled()) {
                mBinding.swipeToLoad.setLoadMoreEnabled(true);
                mBinding.swipeToLoad.setLoadingMore(false);
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }
    }

    /**
     * 加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getList(id,page + "",this.bindToLifecycle());
    }

    /**
     * 数据加载成功
     */
    @Override
    public void onSuccess(FindListBean findListBean) {
        if(refersh){
            stopResher();
            if(findListBean.getData() != null && findListBean.getData().size() != 0){
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(findListBean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
        }else {
            stopLoading();
            if (findListBean.getData() != null && findListBean.getData().size() != 0) {
                mList.addAll(findListBean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                page--;
               ToastUtil.show("已经是最后一页了");
            }
        }
        if(once[0]){
            once[0] = false;
        }
    }

    /**
     * 数据加载失败
     */
    @Override
    public void onFile(String error) {
        if(refersh){
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
        if(once[0]){
            once[0] = false;
        }
    }

}
