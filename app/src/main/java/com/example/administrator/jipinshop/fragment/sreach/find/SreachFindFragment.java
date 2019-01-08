package com.example.administrator.jipinshop.fragment.sreach.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class SreachFindFragment extends DBBaseFragment {

    private Boolean once = true;//记录第一次进入页面标示

    public static SreachFindFragment getInstance(String content) {
        SreachFindFragment fragment = new SreachFindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {

        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_sreachfind,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
