package com.example.administrator.jipinshop.fragment.home.commen.tabitem;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.SreachGoodsAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.databinding.FragmentHomeCommenBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe 个护健康榜二级目录详情页面
 */
public class ItemTabCommenFragment extends DBBaseFragment implements ItemTabCommenView, SreachGoodsAdapter.OnItem, OnRefreshListener {

    @Inject
    ItemTabCommenPresenter mPresenter;

    private FragmentHomeCommenBinding mBinding;
    private int position;//记录这是第几个fragment; 从0开始
    private SreachGoodsAdapter mAdapter;
    private List<SreachResultGoodsBean.DataBean> mList;
    private String category2Id = "";//二级导航id
    private Boolean[] once = {true};//记录第一次进入页面标示

    public static ItemTabCommenFragment getInstance(int position,String id) {
        ItemTabCommenFragment fragment = new ItemTabCommenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once[0] && position != 0) {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_commen,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        position = getArguments().getInt("position");
        category2Id = getArguments().getString("id");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new SreachGoodsAdapter(mList,getContext());
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad,getContext());
        if (once[0] && position == 0) {
            mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
        }
    }

    @Override
    public void Success(SreachResultGoodsBean resultGoodsBean) {
        if (resultGoodsBean.getData() != null && resultGoodsBean.getData().size() != 0) {
            mList.clear();
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mList.addAll(resultGoodsBean.getData());
            mAdapter.notifyDataSetChanged();
        } else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
        stopResher();
        if (once[0]) {
            once[0] = false;
        }
    }

    @Override
    public void Faile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show("网络出错");
    }

    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mList.get(pos).getPv());
            mList.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mList.get(pos).getGoodsId())
            );
        }
    }

    @Override
    public void onRefresh() {
        if(!TextUtils.isEmpty(category2Id)){
            mPresenter.goodsList2(category2Id,position+"",this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }else {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
            ToastUtil.show("网络请求错误,请重新开启app");
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }

    public void stopResher(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }
}
