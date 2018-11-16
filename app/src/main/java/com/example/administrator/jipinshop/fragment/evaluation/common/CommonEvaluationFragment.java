package com.example.administrator.jipinshop.fragment.evaluation.common;

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
import com.example.administrator.jipinshop.adapter.CommonEvaluationAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private String id = "0";//数据id

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
        if (isVisibleToUser && once) {
            if (!getArguments().getString("type").equals(ONE)) {
                //缓存数据
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
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        //缓存数据
        if (getArguments().getString("type").equals(ONE)) {
            for (int i = 0; i < 10; i++) {
                mList.add("");
            }
        }
        mAdapter = new CommonEvaluationAdapter(mList, getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initDate(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(EvaluationFragment.tag)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab, ""))) {
                EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
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
