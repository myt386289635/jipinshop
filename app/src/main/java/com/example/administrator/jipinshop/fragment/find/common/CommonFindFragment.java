package com.example.administrator.jipinshop.fragment.find.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommonFindAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentFindCommonBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 精选榜、个护健康、厨卫电器、生活家居、家用大电
 */
public class CommonFindFragment extends DBBaseFragment implements OnRefreshListener {

    public static final String ONE = "1"; //该页为精选榜
    public static final String TWO = "2"; //该页为个护健康
    public static final String THREE = "3"; //该页为厨卫电器
    public static final String FORE = "4"; //该页为生活家居
    public static final String FIVE = "5"; //该页为家用大电

    @Inject
    CommonFindPresenter mPresenter;

    private FragmentFindCommonBinding mBinding;
    private List<String> mList;
    private CommonFindAdapter mAdapter;

    private Boolean once = true;

    public static CommonFindFragment getInstance(String type) {
        CommonFindFragment fragment = new CommonFindFragment();
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
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_find_common,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new CommonFindAdapter(mList, getContext());
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

    //刷新
    @Override
    public void onRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        mAdapter.notifyDataSetChanged();
    }
}
