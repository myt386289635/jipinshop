package com.example.administrator.jipinshop.fragment.member;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.MemberMoreAdapter;
import com.example.administrator.jipinshop.adapter.MemberShopAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.FragmentMemberNewBinding;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/8/25
 * @Describe 新版会员页面
 */
public class MemberFragment extends DBBaseFragment implements View.OnClickListener, OnRefreshListener, MemberView {

    @Inject
    MemberPresenter mPresenter;
    private FragmentMemberNewBinding mBinding;
    private String type = "1";// 1:fragment 2:activity
    private Boolean once = true;
    //广告
    private List<MemberNewBean.DataBean.MessageListBean> mAdList;
    //更多权益
    private List<MemberNewBean.DataBean.VipBoxListBean> mMoreList;
    private MemberMoreAdapter mMoreAdapter;
    //商品列表
    private List<MemberNewBean.DataBean.OrderLevelDataBean.OrderListBean> mOrderList;
    private MemberShopAdapter mShopAdapter;

    public static MemberFragment getInstance(String type){
        MemberFragment fragment = new MemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);//1:fragment 2:activity
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            mBinding.swipeToLoad.setRefreshing(true);
            once = false;
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_new,container,false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mBinding.swipeToLoad.setOnRefreshListener(this);
        type = getArguments().getString("type","1");

        //广告
        mAdList = new ArrayList<>();
        //更多权益
        mMoreList = new ArrayList<>();
        mMoreAdapter = new MemberMoreAdapter(mMoreList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        mBinding.memberMore.setLayoutManager(linearLayoutManager);
        mBinding.memberMore.setNestedScrollingEnabled(false);
        mBinding.memberMore.setAdapter(mMoreAdapter);
        mBinding.memberMore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //划出去的宽度
                int isResult = getResult(linearLayoutManager);
                //可划出去的总宽度
                double totleWith = getTotleWith(linearLayoutManager);
                //线条可划出去的总宽度
                double lineWith = getContext().getResources().getDimension(R.dimen.x60);
                //结果
                double result  = (lineWith / totleWith) * isResult;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.memberPoint.getLayoutParams();
                layoutParams.leftMargin = (int) result;
                mBinding.memberPoint.setLayoutParams(layoutParams);
            }
        });
        //商品列表
        mOrderList = new ArrayList<>();
        mShopAdapter = new MemberShopAdapter(mOrderList,getContext());
        mBinding.memberShopList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.memberShopList.setNestedScrollingEnabled(false);
        mBinding.memberShopList.setAdapter(mShopAdapter);

        if (type.equals("2")){
            mBinding.swipeToLoad.post(() -> {
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            });
            mBinding.memberBlack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        mPresenter.levelIndex(this.bindToLifecycle());
    }

    private int getResult(LinearLayoutManager linearLayoutManager){
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        //获取Item的宽
        int itemWidth = 0;
        //算出该Item还未移出屏幕的高度
        int itemRight = 0;
        if (firstVisiableChildView != null){
            itemWidth = firstVisiableChildView.getWidth();
            itemRight = firstVisiableChildView.getRight();
        }
        //position移出屏幕的数量*高度得出移动的距离
        int iposition = position * itemWidth;
        //因为横着的RecyclerV第一个取到的Item position为零所以计算时需要加一个宽
        int iResult = iposition - itemRight + itemWidth;
        return  iResult;
    }

    private double getTotleWith(LinearLayoutManager linearLayoutManager){
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        //获取Item的宽
        int itemWidth = 0;
        if (firstVisiableChildView != null){
            itemWidth = firstVisiableChildView.getWidth();
        }
        double zWidth = (DistanceHelper.getAndroiodScreenwidthPixels(getContext()) - getContext().getResources().getDimension(R.dimen.x20) - getContext().getResources().getDimension(R.dimen.x20));
        return ((itemWidth * mMoreList.size()) - zWidth);
    }

    @Override
    public void onSuccess(MemberNewBean bean) {
        mBinding.swipeToLoad.setRefreshing(false);
        mBinding.setBean(bean.getData());
        mBinding.executePendingBindings();
        //广告
        mAdList.clear();
        mAdList.addAll(bean.getData().getMessageList());
        mPresenter.adFlipper(getContext(),mBinding.viewFlipper,mAdList);
        //更多权益
        mMoreList.clear();
        mMoreList.addAll(bean.getData().getVipBoxList());
        if (mMoreList.size() > 3){
            mBinding.memberPointContainer.setVisibility(View.VISIBLE);
        }else{
            mBinding.memberPointContainer.setVisibility(View.GONE);
        }
        mMoreAdapter.notifyDataSetChanged();
        //商品列表
        mOrderList.clear();
        mOrderList.addAll(bean.getData().getOrderLevelData().getOrderList());
        mShopAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String error) {
        mBinding.swipeToLoad.setRefreshing(false);
        ToastUtil.show(error);
    }
}
