package com.example.administrator.jipinshop.fragment.home.commen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;

/**
 * @author 莫小婷
 * @create 2019/1/4
 * @Describe 榜单的公共类
 */
public class HomeCommenFragment extends DBBaseFragment {

    public static HomeCommenFragment getInstance() {
        HomeCommenFragment fragment = new HomeCommenFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_commen,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
