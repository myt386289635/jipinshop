package com.example.administrator.jipinshop.activity.balance.budget;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.BudgetOneAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.BudgetDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityBudgetDetailBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe 收支明细
 */
public class BudgetDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityBudgetDetailBinding mBinding;
    private List<BudgetDetailBean.DataBean> mList;
    private BudgetOneAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_budget_detail);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("收入明细");

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        BudgetDetailBean mBudgetDetailBean = (BudgetDetailBean) getIntent().getSerializableExtra("date");
        if (mBudgetDetailBean != null) mList.addAll(mBudgetDetailBean.getData());
        mAdapter = new BudgetOneAdapter(mList,this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.scrollToPosition(getIntent().getIntExtra("position",0));
        mBinding.recyclerView.setItemViewCacheSize(5);
        mBinding.recyclerView.setDrawingCacheEnabled(true);//耗内存
        mBinding.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);//耗内存
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
