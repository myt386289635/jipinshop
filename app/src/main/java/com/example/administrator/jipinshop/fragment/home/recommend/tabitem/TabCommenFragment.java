package com.example.administrator.jipinshop.fragment.home.recommend.tabitem;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.HomeCommenAdapter;
import com.example.administrator.jipinshop.adapter.TabCommenAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.databinding.FragmentRecommendBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe 热卖榜二级目录公共详情页面
 */
public class TabCommenFragment extends DBBaseFragment implements TabCommenView, TabCommenAdapter.OnItem, OnRefreshListener {

    @Inject
    TabCommenPersenter mPresenter;

    protected FragmentRecommendBinding mBinding;
    private int position;//记录这是第几个fragment; 从0开始
    private TabCommenAdapter mAdapter;
    private List<HomeCommenBean.DataBean> mCommenBeans;
    private String category1Id = "";//一级导航id
    private Boolean[] once = {true};//记录第一次进入页面标示

    public static TabCommenFragment getInstance(int position,String orderbyType) {
        TabCommenFragment fragment = new TabCommenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("orderbyType",orderbyType);
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_recommend,container,false);
        return  mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        position = getArguments().getInt("position");
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))) {
            TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);
            category1Id = tabBean.getData().get(position).getCategoryId();
        } else {
            category1Id = "";
        }

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommenBeans = new ArrayList<>();
        mAdapter = new TabCommenAdapter(mCommenBeans,getContext());
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad,getContext());
        if (once[0] && position == 0) {
            mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
        }
    }

    @Override
    public void onSuccess(HomeCommenBean commenBean) {
        if (commenBean.getData() != null && commenBean.getData().size() != 0) {
            mCommenBeans.clear();
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mCommenBeans.addAll(commenBean.getData());
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
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show("网络出错");
    }

    @Override
    public void onItemclick(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mCommenBeans.get(pos).getPv());
            mCommenBeans.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mCommenBeans.get(pos).getGoodsId())
            );
        }
    }

    @Override
    public void onRefresh() {
        if(!TextUtils.isEmpty(category1Id)){
            mPresenter.goodRank(category1Id,getArguments().getString("orderbyType"),this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
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
