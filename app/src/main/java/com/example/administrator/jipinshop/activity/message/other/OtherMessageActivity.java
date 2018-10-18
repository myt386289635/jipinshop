package com.example.administrator.jipinshop.activity.message.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.OtherMessageAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 极品成活动、评测赢大奖
 */
public class OtherMessageActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    OtherMessagePresenter mPresenter;
    private OtherMessageAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_other);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText(getIntent().getStringExtra("title"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new OtherMessageAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }
}
