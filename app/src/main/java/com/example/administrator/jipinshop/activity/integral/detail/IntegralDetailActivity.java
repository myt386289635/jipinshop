package com.example.administrator.jipinshop.activity.integral.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.IntegralDetailAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;

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
public class IntegralDetailActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    IntegralDetailPresenter mPresenter;

    private List<String> mList;
    private IntegralDetailAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_detail);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv.setText("积分明细");

        mList = new ArrayList<>();
        mAdapter = new IntegralDetailAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }
}
