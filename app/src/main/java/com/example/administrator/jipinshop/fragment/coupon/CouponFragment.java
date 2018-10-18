package com.example.administrator.jipinshop.fragment.coupon;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CouponAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentCouponBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class CouponFragment extends DBBaseFragment implements OnRefreshListener {

    @Inject
    CouponPresenter mPresenter;

    private FragmentCouponBinding mBinding;
    private CouponAdapter mAdapter;
    private List<String> mList;

    private Boolean once = true;

    public static CouponFragment getInstance(String type) {
        CouponFragment fragment = new CouponFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            if(!getArguments().getString("type").equals("1")){
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            }
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_coupon,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);

        mList = new ArrayList<>();
        mAdapter = new CouponAdapter(mList,getContext(),getArguments().getString("type"));
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        if(getArguments().getString("type").equals("1")){
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
    }
}
