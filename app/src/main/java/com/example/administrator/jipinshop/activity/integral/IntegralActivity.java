package com.example.administrator.jipinshop.activity.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.integral.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.IntegralAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 我的积分
 */
public class IntegralActivity extends BaseActivity implements IntegralAdapter.OnItemListener {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.integral_rule)
    TextView mIntegralRule;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    IntegralPresenter mPresenter;
    private List<String> mList;
    private IntegralAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mTitleTv.setText("我的积分");

        mList = new ArrayList<>();
        mAdapter = new IntegralAdapter(this,mList);
        mAdapter.setOnItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.title_back, R.id.integral_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.integral_rule:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"jpc-info/src/my/integral-rule.html")
                        .putExtra(WebActivity.title,"积分规则")
                );
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    /**
     * 点击去兑换
     * @param pos
     */
    @Override
    public void onItemExchange(int pos) {
        startActivity(new Intent(this,ShoppingDetailActivity.class));
    }

    /**
     * 点击积分明细
     */
    @Override
    public void onItemIntegralDetail() {
        startActivity(new Intent(this,IntegralDetailActivity.class));
    }
}
