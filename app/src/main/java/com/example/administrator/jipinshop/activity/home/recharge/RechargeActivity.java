package com.example.administrator.jipinshop.activity.home.recharge;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.RechargeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.databinding.ActivityRechargeBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/1/18
 * @Describe 充值集合页面
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, RechargeView {

    @Inject
    RechargePresenter mPresenter;

    private ActivityRechargeBinding mBinding;
    private List<EvaluationTabBean.DataBean.AdListBean> mList;
    private RechargeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_recharge);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("充值返现");

        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mList = new ArrayList<>();
        mAdapter = new RechargeAdapter(mList,this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setLoadMoreEnabled(false);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.adList(this.bindToLifecycle());
    }

    @Override
    public void onSuccess(SucBean<EvaluationTabBean.DataBean.AdListBean> bean) {
        dissRefresh();
        if (bean.getData() != null && bean.getData().size() != 0){
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            ToastUtil.show("没有数据");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFile(String error) {
        dissRefresh();
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show(error);
    }

    public void dissRefresh(){
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

}
