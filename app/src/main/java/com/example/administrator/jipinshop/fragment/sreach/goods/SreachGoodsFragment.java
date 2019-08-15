package com.example.administrator.jipinshop.fragment.sreach.goods;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.adapter.SreachGoodsAdapter2;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SreachBean;
import com.example.administrator.jipinshop.bean.eventbus.SreachBus;
import com.example.administrator.jipinshop.databinding.FragmentSreachgoodsBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe 榜单
 */
public class SreachGoodsFragment extends DBBaseFragment implements OnRefreshListener, SreachGoodsView, OnLoadMoreListener, SreachGoodsAdapter2.OnItem {

    @Inject
    SreachGoodsPresenter mPresenter;
    private FragmentSreachgoodsBinding mBinding;
    private SreachGoodsAdapter2 mAdapter;
    private List<SreachBean.DataBean> mList;
    private List<SreachBean.GoodsCategoryListBean> goodsCategoryList;

    private int page = 1;
    private Boolean refersh = true;

    public static SreachGoodsFragment getInstance(String content) {
        SreachGoodsFragment fragment = new SreachGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sreachgoods,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        goodsCategoryList = new ArrayList<>();
        mAdapter = new SreachGoodsAdapter2(getContext(),mList,goodsCategoryList);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,((SreachResultActivity)getActivity()).getBar());
        mBinding.swipeToLoad.setRefreshing(true);
    }

    /**
     * item点击
     */
    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mList.get(pos).getGoodsId())
            );
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.searchGoods(page + "" ,getArguments().getString("content"),this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }


    @Override
    public void Success(SreachBean resultGoodsBean) {
        EventBus.getDefault().post(new SreachBus(SreachActivity.sreachHistoryTag));
        stopResher();
        stopLoading();
        if((resultGoodsBean.getData() != null && resultGoodsBean.getData().size() != 0) ||
                (resultGoodsBean.getGoodsCategoryList() != null && resultGoodsBean.getGoodsCategoryList().size() != 0)){
            if(refersh){
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                goodsCategoryList.clear();
                mList.addAll(resultGoodsBean.getData());
                goodsCategoryList.addAll(resultGoodsBean.getGoodsCategoryList());
                mAdapter.notifyDataSetChanged();
            }else {
                if (resultGoodsBean.getData() != null && resultGoodsBean.getData().size() != 0){
                    mList.addAll(resultGoodsBean.getData());
                    mAdapter.notifyDataSetChanged();
                    mBinding.swipeToLoad.setLoadMoreEnabled(false);
                }else{
                    page-- ;
                    ToastUtil.show("已经是最后一页了");
                }
            }
        }else {
            if(refersh){
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }else {
                page-- ;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    @Override
    public void Faile(String error) {
        if(refersh){
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
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
    /**
     * 停止加载
     */
    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled()) {
                mBinding.swipeToLoad.setLoadMoreEnabled(true);
                mBinding.swipeToLoad.setLoadingMore(false);
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.searchGoods(page + "" ,getArguments().getString("content"),this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
}
