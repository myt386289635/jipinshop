package com.example.administrator.jipinshop.fragment.evaluation;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationBinding;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EvaluationFragment extends DBBaseFragment {


    @Inject
    EvaluationFragmentPresenter mPresenter;

    private FragmentEvaluationBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_evaluation,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());

        mFragments = new ArrayList<>();
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.ONE));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.TWO));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.THREE));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.FORE));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.FIVE));

        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(4);
        mBinding.tabLayout.setupWithViewPager( mBinding.viewPager);

        mPresenter.initTabLayout(getContext(),mBinding.tabLayout);
    }
}
