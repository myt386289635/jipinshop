package com.example.administrator.jipinshop.fragment.tryout.freemodel.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.ShopDescriptionAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe 免单详情——商品介绍
 */
public class ShopDescriptionFragment extends DBBaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private Unbinder unbinder;
    private ShopDescriptionAdapter mRVAdapter;
    private List<FreeDetailBean.DataBean.GoodsContentListBean> mList;

    public static ShopDescriptionFragment getInstance(String date) {
        ShopDescriptionFragment fragment = new ShopDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gson", date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_free_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {
        mList = new ArrayList<>();
        if (getArguments() != null)
            mList.addAll(new Gson().fromJson(getArguments().getString("gson"), new TypeToken<List<FreeDetailBean.DataBean.GoodsContentListBean>>() {}.getType()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRVAdapter = new ShopDescriptionAdapter(getContext(),mList);
        mRecyclerView.setAdapter(mRVAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
