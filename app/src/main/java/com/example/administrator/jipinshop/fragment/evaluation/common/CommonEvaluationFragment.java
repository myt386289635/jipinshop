package com.example.administrator.jipinshop.fragment.evaluation.common;

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
import com.example.administrator.jipinshop.adapter.CommonEvaluationAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommonEvaluationFragment extends DBBaseFragment implements OnRefreshListener, CommonEvaluationView, OnLoadMoreListener{

    @Inject
    CommonEvaluationPresenter mPresenter;

    private FragmentEvaluationCommonBinding mBinding;
    private List<EvaluationListBean.DataBean> mList;
    private CommonEvaluationAdapter mAdapter;

    private Boolean[] once = {true};
    private String id = "0";//数据id
    private int page = 1;
    private Boolean refersh = true;

    public static CommonEvaluationFragment getInstance(int type) {
        CommonEvaluationFragment fragment = new CommonEvaluationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once[0]) {
            if(getArguments().getInt("type") != 0){
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.getDate(id, page + "", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new CommonEvaluationAdapter(mList, getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad,getContext());
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initDate(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(EvaluationFragment.tag)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab, ""))) {
                EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                id = bean.getData().get(getArguments().getInt("type")).getCategoryId();
            } else {
                id = "0";
            }
            if (getArguments().getInt("type") == 0) {
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    /**
     * 数据成功回调
     *
     * @param evaluationListBean
     */
    @Override
    public void onSuccess(EvaluationListBean evaluationListBean) {
        if (refersh) {
            stopResher();
            if (evaluationListBean.getData() != null && evaluationListBean.getData().size() != 0) {
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(evaluationListBean.getData());
                mAdapter.notifyDataSetChanged();
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            stopLoading();
            if (evaluationListBean.getData() != null && evaluationListBean.getData().size() != 0) {
                mList.addAll(evaluationListBean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    /**
     * 数据失败回调
     *
     * @param error
     */
    @Override
    public void onFile(String error) {
        if (refersh) {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        } else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
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
        mPresenter.getDate(id, page + "", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
}
