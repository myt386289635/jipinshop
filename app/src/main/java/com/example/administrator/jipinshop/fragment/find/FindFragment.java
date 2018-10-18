package com.example.administrator.jipinshop.fragment.find;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentFindBinding;
import com.example.administrator.jipinshop.fragment.find.common.CommonFindFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FindFragment extends DBBaseFragment {

    @Inject
    FindFragmentPresenter mPresenter;

    private FragmentFindBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    private Boolean once = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            mFragments.add(CommonFindFragment.getInstance(CommonFindFragment.ONE));
            mFragments.add(CommonFindFragment.getInstance(CommonFindFragment.TWO));
            mFragments.add(CommonFindFragment.getInstance(CommonFindFragment.THREE));
            mFragments.add(CommonFindFragment.getInstance(CommonFindFragment.FORE));
            mFragments.add(CommonFindFragment.getInstance(CommonFindFragment.FIVE));
            mAdapter.notifyDataSetChanged();
            mBinding.viewPager.setOffscreenPageLimit(4);
            mPresenter.initTabLayout(getContext(),mBinding.tabLayout);
            once = false;
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_find,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());

        mFragments = new ArrayList<>();

        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager( mBinding.viewPager);
    }
}
