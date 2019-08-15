package com.example.administrator.jipinshop.fragment.foval.goods;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.FovalGoodsAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.databinding.FragmentSreachgoodsBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/15
 * @Describe
 */
public class FovalGoodsFragment extends DBBaseFragment implements FovalGoodsAdapter.OnItem, OnRefreshListener, OnLoadMoreListener, FovalGoodsView {

    public static final String CollectResher = "ShoppingDetailActivity2FovalGoodsFragment";

    @Inject
    FovalGoodsPersenter mPresenter;
    private FragmentSreachgoodsBinding mBinding;
    private FovalGoodsAdapter mAdapter;
    private List<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> mList;

    private int page = 1;
    private Boolean refersh = true;

    public static FovalGoodsFragment getInstance() {
        FovalGoodsFragment fragment = new FovalGoodsFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sreachgoods,container,false);
        return mBinding.getRoot();
    }


    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setGoodsView(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new FovalGoodsAdapter(mList, getContext());
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        } else {
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId", mList.get(pos).getGoodsId())
            );
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.collect(page, this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.collect(page, this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
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
    public void Success(SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> resultGoodsBean) {
        stopResher();
        stopLoading();
        if(resultGoodsBean.getData() != null && resultGoodsBean.getData().size() != 0){
            if(refersh){
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(resultGoodsBean.getData());
                mAdapter.notifyDataSetChanged();
            } else {
                mList.addAll(resultGoodsBean.getData());
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

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onResher(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(FovalGoodsFragment.CollectResher)) {
            onRefresh();
        }
    }
}
