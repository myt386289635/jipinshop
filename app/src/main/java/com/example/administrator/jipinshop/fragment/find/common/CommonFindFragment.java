package com.example.administrator.jipinshop.fragment.find.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommonFindAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.eventbus.FindTabBus;
import com.example.administrator.jipinshop.databinding.FragmentFindCommonBinding;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private String id = "0";//数据id

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
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        if(getArguments().getString("type").equals(ONE)){
            for (int i = 0; i < 10; i++) {
                mList.add("");
            }
        }
        mAdapter = new CommonFindAdapter(mList, getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
    }

    //刷新
    @Override
    public void onRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initDate(FindTabBus findTabBus) {
        if (findTabBus != null && findTabBus.getTag().equals(FindFragment.tag)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.FindTab, ""))) {
                EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.FindTab), EvaluationTabBean.class);
                if (getArguments().getString("type").equals(ONE)) {
                    id = bean.getList().get(0).getCategoryId();
                } else if (getArguments().getString("type").equals(TWO)) {
                    id = bean.getList().get(1).getCategoryId();
                } else if (getArguments().getString("type").equals(THREE)) {
                    id = bean.getList().get(2).getCategoryId();
                } else if (getArguments().getString("type").equals(FORE)) {
                    id = bean.getList().get(3).getCategoryId();
                } else if (getArguments().getString("type").equals(FIVE)) {
                    id = bean.getList().get(4).getCategoryId();
                }
            } else {
                id = "0";
            }
            if (getArguments().getString("type").equals(ONE)) {
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }
}
