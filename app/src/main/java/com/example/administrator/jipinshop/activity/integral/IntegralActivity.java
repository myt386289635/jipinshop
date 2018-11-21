package com.example.administrator.jipinshop.activity.integral;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.integral.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.adapter.IntegralAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.IntegralShopBean;
import com.example.administrator.jipinshop.databinding.ActivityIntegralBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 我的积分
 */
public class IntegralActivity extends BaseActivity implements IntegralAdapter.OnItemListener, IntegralView, View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    @Inject
    IntegralPresenter mPresenter;

    private ActivityIntegralBinding mBinding;
    private List<IntegralShopBean.ListBean> mList;
    private IntegralAdapter mAdapter;
    private int page = 0;
    private boolean refersh = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_integral);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.swipeTarget.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.inClude.titleTv.setText("我的积分");

        mList = new ArrayList<>();
        mAdapter = new IntegralAdapter(this, mList);
        mAdapter.setOnItemListener(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mBinding.swipeTarget, this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    /**
     * 点击去兑换
     *
     * @param pos
     */
    @Override
    public void onItemExchange(int pos) {
        Toast.makeText(this, "积分不足", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击积分明细
     */
    @Override
    public void onItemIntegralDetail() {
        startActivity(new Intent(this, IntegralDetailActivity.class));
    }

    @Override
    public void onSuccess(IntegralShopBean shopBean) {
        if (refersh) {
            stopResher();
            mList.clear();
            mList.addAll(shopBean.getList());
            mAdapter.notifyDataSetChanged();
        } else {
            stopLoading();
            if(shopBean.getList().size() != 0){
                mList.addAll(shopBean.getList());
                mAdapter.notifyDataSetChanged();
            }else {
                page--;
                Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFile(String error) {
        if(refersh){
            stopResher();
        }else {
            stopLoading();
            page--;
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.integral_rule:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "integral-rule.html")
                        .putExtra(WebActivity.title, "积分规则")
                );
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        refersh = true;
        mPresenter.getDate(page, this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getDate(page, this.bindToLifecycle());
    }

    /**
     * 停止刷新
     */
    public void stopResher() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
    }

    /**
     * 停止加载
     */
    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
    }

}
