package com.example.administrator.jipinshop.activity.sign.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.IntegralDetailAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityIntegralDetailBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 积分明细
 */
public class IntegralDetailActivity extends BaseActivity implements IntegralDetailView, OnRefreshListener, View.OnClickListener, OnLoadMoreListener {
    @Inject
    IntegralDetailPresenter mPresenter;

    private ActivityIntegralDetailBinding mBinding;
    private List<PointDetailBean.DataBean> mList;
    private IntegralDetailAdapter mAdapter;
    private int page = 1;
    private Boolean refersh = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_integral_detail);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.inClude.titleTv.setText("极币明细");

        mList = new ArrayList<>();
        mAdapter = new IntegralDetailAdapter(this, mList);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(PointDetailBean bean) {
        if(refersh){
            stopResher();
            if (bean.getData() != null && bean.getData().size() != 0) {
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            } else {
                initError(R.mipmap.qs_integral, "暂无极币明细", "可以去签到领极币哦");
            }
        }else {
            stopLoading();
            if(bean.getData() != null && bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }

    }

    /**
     * 失败
     */
    @Override
    public void onFile(String error) {
        if(refersh){
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
            stopResher();
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.getDate(page,this.bindToLifecycle());
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
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getDate(page,this.bindToLifecycle());
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

}
