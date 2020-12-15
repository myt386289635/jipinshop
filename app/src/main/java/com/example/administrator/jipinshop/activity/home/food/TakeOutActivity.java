package com.example.administrator.jipinshop.activity.home.food;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.TakeOutAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/11/26
 * @Describe 外卖集合
 */
public class TakeOutActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, TakeOutView {

    @Inject
    TakeOutPresenter mPresenter;

    private ActivityMessageSystemBinding mBinding;
    private List<EvaluationTabBean.DataBean.AdListBean> mList;
    private TakeOutAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_message_system);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.messageTitle.setVisibility(View.GONE);
        mBinding.inClude.titleTv.setText("外卖返现");

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new TakeOutAdapter(mList,this);
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
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_news, "暂无消息", "还没有任何消息哦，先休息一下吧");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFile(String error) {
        dissRefresh();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
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

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

}
