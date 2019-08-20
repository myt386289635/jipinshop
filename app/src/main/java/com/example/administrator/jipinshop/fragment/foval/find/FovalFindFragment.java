package com.example.administrator.jipinshop.fragment.foval.find;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.SreachArticleAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.databinding.FragmentSreachfindBinding;
import com.example.administrator.jipinshop.fragment.sreach.find.SreachFindView;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/15
 * @Describe 收藏清单、评测
 */
public class FovalFindFragment extends DBBaseFragment implements SreachArticleAdapter.OnItem, OnRefreshListener, OnLoadMoreListener, SreachFindView {

    public static final String CollectResher = "ShoppingDetailActivity2FovalFindFragment";

    private FragmentSreachfindBinding mBinding;
    private Boolean[] once = {true};//记录第一次进入页面标示
    private SreachArticleAdapter mAdapter;
    private List<SreachResultArticlesBean.DataBean> mList;

    private int page = 1;
    private Boolean refersh = true;

    @Inject
    FovalFindPresenter mPresenter;


    public static FovalFindFragment getInstance(String type) {
        FovalFindFragment fragment = new FovalFindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once[0]) {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sreachfind,container,false);
        return mBinding.getRoot();
    }


    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new SreachArticleAdapter(mList, getContext());
        mAdapter.setOnItem(this);
        mAdapter.setfType(getArguments().getString("type"));
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
    }

    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        } else {
            BigDecimal bigDecimal = new BigDecimal(mList.get(pos).getPv());
            mList.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            ShopJumpUtil.jumpArticle(getContext(),mList.get(pos).getArticleId(),
                    getArguments().getString("type"),mList.get(pos).getContentType());
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.collect(page, getArguments().getString("type"),this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.collect(page, getArguments().getString("type"),this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public void Success(SreachResultArticlesBean articlesBean) {
        stopResher();
        stopLoading();
        if(articlesBean.getData() != null && articlesBean.getData().size() != 0){
            if(refersh){
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(articlesBean.getData());
                mAdapter.notifyDataSetChanged();
            } else {
                mList.addAll(articlesBean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }
        }else {
            if(refersh){
                initError(R.mipmap.qs_collection, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }else {
                page-- ;
                ToastUtil.show("已经是最后一页了");
            }
        }
        if (refersh) {
            if (once[0]) {
                once[0] = false;
            }
        }
    }

    @Override
    public void Faile(String error) {
        if (refersh) {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑试试");
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
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onResher(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(FovalFindFragment.CollectResher)) {
            onRefresh();
        }
    }
}
