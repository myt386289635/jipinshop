package com.example.administrator.jipinshop.fragment.home.commen.tabitem;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentHomeCommenBinding;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe 个护健康榜二级目录详情页面
 */
public class ItemTabCommenFragment extends DBBaseFragment{

    private FragmentHomeCommenBinding mBinding;

    public static ItemTabCommenFragment getInstance(int position) {
        ItemTabCommenFragment fragment = new ItemTabCommenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_commen,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {

    }
}
