package com.example.administrator.jipinshop.fragment.evaluation.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommonEvaluationAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommonEvaluationFragment extends DBBaseFragment implements OnRefreshListener {

    public static final String ONE = "1"; //该页为精选榜
    public static final String TWO = "2"; //该页为个护健康
    public static final String THREE = "3"; //该页为厨卫电器
    public static final String FORE = "4"; //该页为生活家居
    public static final String FIVE = "5"; //该页为家用大电

    @Inject
    CommonEvaluationPresenter mPresenter;

    private FragmentEvaluationCommonBinding mBinding;
    private List<String> mList;
    private CommonEvaluationAdapter mAdapter;

    private Boolean once = true;

    public static CommonEvaluationFragment getInstance(String type) {
        CommonEvaluationFragment fragment = new CommonEvaluationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            if(!getArguments().getString("type").equals(ONE)){
                for (int i = 0; i < 10; i++) {
                    mList.add("");
                }
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_evaluation_common,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new CommonEvaluationAdapter(mList, getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        if(getArguments().getString("type").equals(ONE)){
            for (int i = 0; i < 10; i++) {
                mList.add("");
            }
            mAdapter.notifyDataSetChanged();
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }
}
