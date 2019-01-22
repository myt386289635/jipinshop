package com.example.administrator.jipinshop.fragment.follow.fans;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.FansAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.FollowBus;
import com.example.administrator.jipinshop.databinding.FragmentFollowBinding;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.util.ToastUtil;

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
public class FansFragment extends DBBaseFragment implements OnRefreshListener, FansView, FansAdapter.OnItemClick, OnLoadMoreListener {

    public static final String refreshFans = "refreshFans";

    @Inject
    FansPresenter mPresenter;

    private FragmentFollowBinding mBinding;
    private Boolean once = true;
    private List<FollowBean.DataBean> mList;
    private FansAdapter mAdapter;
    //页数
    private int page = 1;
    private Boolean refresh = true;//判断是刷新还是加载

    public static FansFragment getInstance(int page) {
        FansFragment fragment = new FansFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page",page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once && getArguments().getInt("page") != 1){
            mBinding.swipeToLoad.setRefreshing(true);
            once = false;
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new FansAdapter(mList,getContext());
        mAdapter.setOnItemClick(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        if(once && getArguments().getInt("page") == 1){
            mBinding.swipeToLoad.setRefreshing(true);
            once = false;
        }

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,getContext());
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh = true;
        mPresenter.fansList(page,this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refresh = false;
        mPresenter.fansList(page,this.bindToLifecycle());
    }

    @Override
    public void FollowSuccess(FollowBean followBean) {
        if(refresh){
            dissRefresh();
        }else {
            dissLoading();
        }
        if (followBean.getData() != null && followBean.getData().size() != 0) {
            //数据不为空时
            EventBus.getDefault().post(new EditNameBus(FansFragment.refreshFans,followBean.getTotal()+""));
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            if(refresh){
                mList.clear();
                mList.addAll(followBean.getData());
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setFollow(1);
                }
            }else {
                int count = mList.size();
                mList.addAll(followBean.getData());
                for (int i = count; i < mList.size(); i++) {
                    mList.get(i).setFollow(1);
                }
            }
            mAdapter.notifyDataSetChanged();
        }else {
            if(!refresh){
                //加载数据为空时
                page--;
                ToastUtil.show("已经是最后一页了");
            }else {
                //刷新数据为空时
                initError(R.mipmap.qs_collection, "暂无粉丝", "粉丝内容为空，先去逛逛吧");
            }
        }
    }

    @Override
    public void FollowFaileCode(String error) {
        //请求成功，但服务器拒绝返回数据
        if(refresh){
            //刷新时
            dissRefresh();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑试试");
        }else {
            //加载时
            dissLoading();
            page--;
            ToastUtil.show(error);
        }
    }

    @Override
    public void ConcerDelSuccess(SuccessBean successBean, int pos) {
        EventBus.getDefault().post(new FollowBus(AttentionFragment.refreshAttention));
        mList.get(pos).setStatus(0);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void ConcerDelFaile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void concerInsSuccess(SuccessBean successBean, int pos) {
        EventBus.getDefault().post(new FollowBus(AttentionFragment.refreshAttention));
        mList.get(pos).setStatus(1);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItem(int pos) {
        // TODO: 2019/1/16 跳转到个人主页
    }

    @Override
    public void onItemAtten(int pos) {
        mPresenter.concernInsert(pos,mList.get(pos).getUserId(),this.bindToLifecycle());
    }

    @Override
    public void onItemDecAtten(int pos, TextView textView) {
        mPresenter.concernDelete(pos,mList.get(pos).getUserId(),this.bindToLifecycle());
    }

    /**
     * 停止刷新
     */
    public void dissRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    /**
     * 停止加载
     */
    public void dissLoading() {
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

    public void initError(int id, String title, String content) {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshFans(FollowBus bus){
        if(bus != null && bus.getTag().equals(FansFragment.refreshFans)){
            mBinding.recyclerView.scrollToPosition(0);
            mBinding.swipeToLoad.setRefreshEnabled(true);
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }
}
