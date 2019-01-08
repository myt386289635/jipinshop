package com.example.administrator.jipinshop.fragment.sreach.article;

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
public class SreachArticleFragment extends DBBaseFragment {

    public static SreachArticleFragment getInstance(String content,String type) {
        SreachArticleFragment fragment = new SreachArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_sreacharticle,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
