package com.example.administrator.jipinshop.activity.integral.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.IntegralDetailAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.PointDetailBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 积分明细
 */
public class IntegralDetailActivity extends BaseActivity implements IntegralDetailView, OnRefreshListener {
    @Inject
    IntegralDetailPresenter mPresenter;

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.error_image)
    ImageView mErrorImage;
    @BindView(R.id.error_title)
    TextView mErrorTitle;
    @BindView(R.id.error_content)
    TextView mErrorContent;
    @BindView(R.id.qs_net)
    LinearLayout mQsNet;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeToLoad)
    SwipeToLoadLayout mSwipeToLoad;

    private List<PointDetailBean.PointDetailListBean> mList;
    private IntegralDetailAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_detail);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTitleBack.setOnClickListener(v -> finish());
        mTitleTv.setText("积分明细");

        mList = new ArrayList<>();
        mAdapter = new IntegralDetailAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeToLoad.setOnRefreshListener(this);
        mSwipeToLoad.setRefreshing(true);
    }


    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(PointDetailBean bean) {
        if(mSwipeToLoad != null && mSwipeToLoad.isRefreshing()){
            mSwipeToLoad.setRefreshing(false);
        }
        if (bean.getPointDetailList() != null && bean.getPointDetailList().size() != 0) {
            mQsNet.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(bean.getPointDetailList());
            mAdapter.notifyDataSetChanged();
        } else {
            initError(R.mipmap.qs_integral, "暂无积分明细", "可以去签到领积分哦");
        }
    }

    /**
     * 失败
     */
    @Override
    public void onFile(String error) {
        if(mSwipeToLoad != null && mSwipeToLoad.isRefreshing()){
            mSwipeToLoad.setRefreshing(false);
        }
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void initError(int id, String title, String content) {
        mQsNet.setVisibility(View.VISIBLE);
        mErrorImage.setBackgroundResource(id);
        mErrorTitle.setText(title);
        mErrorContent.setText(content);
    }

    @Override
    public void onRefresh() {
        mPresenter.getDate(this.bindToLifecycle());
    }
}
