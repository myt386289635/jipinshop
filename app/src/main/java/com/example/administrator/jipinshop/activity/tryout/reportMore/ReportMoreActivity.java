package com.example.administrator.jipinshop.activity.tryout.reportMore;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.adapter.TryCommenAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.TryReportBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe 试用品查看全部试用报告
 */
public class ReportMoreActivity extends BaseActivity implements View.OnClickListener, ReportMoreView, OnRefreshListener, OnLoadMoreListener, TryCommenAdapter.OnItemClick {

    @Inject
    ReportMorePresenter mPresenter;

    private ActivityMessageSystemBinding mBinding;
    private List<TryReportBean.DataBean> mList;
    private TryCommenAdapter mAdapter;

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
        mBinding.inClude.titleTv.setText("所有试用报告");
        mBinding.swipeToLoad.setBackgroundColor(Color.WHITE);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new TryCommenAdapter(this, mList);
        mAdapter.setOnItemClick(this);
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
    public void onSuccess(TryReportBean bean) {
        if(refersh){
            dissRefresh();
        }else {
            dissLoading();
        }
        if (bean.getData() != null && bean.getData().size() != 0){
            //有数据
            mBinding.netClude.qsNet.setVisibility(View.GONE);
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
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据");
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
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.reportAllList(page + "" , getIntent().getStringExtra("id") , this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.reportAllList(page + "" , getIntent().getStringExtra("id") , this.bindToLifecycle());
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
    public void onItemReportClick(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mList.get(position).getPv());
            mList.get(position).setPv((bigDecimal.intValue() + 1));
            mAdapter.notifyDataSetChanged();
            if (mList.get(position).getContentType() == 1){
                startActivity(new Intent(this,ArticleDetailActivity.class)
                        .putExtra("id",mList.get(position).getArticleId())
                        .putExtra("type","4")
                );
            }else if (mList.get(position).getContentType() == 3){
                startActivity(new Intent(this,ReportDetailActivity.class)
                        .putExtra("id",mList.get(position).getArticleId())
                        .putExtra("type","4")
                );
            }
        }
    }
}
