package com.example.administrator.jipinshop.activity.mall;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.adapter.MallAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城
 */
public class MallActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, MallAdapter.OnItemListener, MallView {

    @Inject
    MallPresenter mPresenter;
    private ActivityMessageSystemBinding mBinding;

    private List<MallBean.DataBean> mList;
    private MallAdapter mAdapter;

    /**
     * 页数
     */
    private int page = 1;
    /**
     * 记录是刷新还是加载
     */
    private Boolean refersh = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_message_system);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("极币商城");
        mBinding.swipeToLoad.setBackgroundColor(Color.WHITE);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mList = new ArrayList<>();
        mAdapter = new MallAdapter(this, mList);
        mAdapter.setOnItemListener(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
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
        page = 1;
        refersh = true;
        mPresenter.mallList(page,this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.mallList(page,this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * 跳转极币商城详情页
     */
    @Override
    public void onItemIntegralDetail(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivityForResult(new Intent(this, MallDetailActivity.class)
                    .putExtra("goodsId",mList.get(position).getId())
                    .putExtra("isActivityGoods",mList.get(position).getType())
            ,300);
        }
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

    public void dissLoading(){
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

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onSuccess(MallBean bean) {
        if(refersh){
            dissRefresh();
        }else {
            dissLoading();
        }
        if (bean.getData() != null && bean.getData().size() != 0){
            //有数据
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            if(refersh){
                mList.clear();
            }
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
            if(!refersh){
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }
        }else {
            //没有数据
            if(refersh){
                //刷新时
                initError(R.mipmap.qs_nodata, "暂无商品", "还没有任何商品哦!~");
                mBinding.recyclerView.setVisibility(View.GONE);
            }else {
                //加载时
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    @Override
    public void onFile(String error) {
        if(refersh){
            dissRefresh();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            dissLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 300){
            finish();
        }
    }
}
