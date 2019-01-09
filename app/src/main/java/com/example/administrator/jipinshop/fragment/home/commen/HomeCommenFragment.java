package com.example.administrator.jipinshop.fragment.home.commen;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.HomeCommenAdapter;
import com.example.administrator.jipinshop.adapter.HomeCommenTabAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ChildrenTabBean;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.databinding.FragmentHomeCommenBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/4
 * @Describe 榜单的公共类
 */
public class HomeCommenFragment extends DBBaseFragment implements HomeCommenTabAdapter.OnItem, OnRefreshListener, HomeCommenAdapter.OnItem, HomeCommenView {

    @Inject
    HomeCommenPresenter mPresenter;

    private FragmentHomeCommenBinding mBinding;
    private List<ChildrenTabBean> mChildrenBeans;
    private int position;//记录这是第几个fragment; 从1开始
    private String image = "";//头image

    private HomeCommenTabAdapter mTabAdapter;
    private HomeCommenAdapter mAdapter;
    private List<HomeCommenBean.DataBean> mCommenBeans;

    private int sets = 0;//二级导航点击的位置   默认为第0个  这个也是最后一次点击的位置
    private Dialog mDialog;//点击二级菜单时请求数据加载框
    private Boolean[] once = {true};//记录第一次进入页面标示

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once[0]) {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }


    public static HomeCommenFragment getInstance(int set) {
        HomeCommenFragment fragment = new HomeCommenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("set",set);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_commen,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        position = getArguments().getInt("set",0);

        mChildrenBeans = new ArrayList<>();
        mTabAdapter = new HomeCommenTabAdapter(mChildrenBeans,getContext());
        mTabAdapter.setOnItem(this);
        mBinding.categoryView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false));
        mBinding.categoryView.setAdapter(mTabAdapter);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommenBeans = new ArrayList<>();
        mAdapter = new HomeCommenAdapter(mCommenBeans,getContext());
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,((HomeFragment)getParentFragment()).getBar(),once);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initSubTab(String string) {
        if (string.equals(HomeFragment.subTab)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))) {
                mBinding.categoryView.setVisibility(View.VISIBLE);
                TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);
                mChildrenBeans.clear();
                for (int i = 0; i < tabBean.getData().get(position).getChildren().size(); i++) {
                    if (i == 0) {
                        mChildrenBeans.add(new ChildrenTabBean(tabBean.getData().get(position).getChildren().get(i).getCategoryName(), true,tabBean.getData().get(position).getChildren().get(i).getCategoryId()));
                    } else {
                        mChildrenBeans.add(new ChildrenTabBean(tabBean.getData().get(position).getChildren().get(i).getCategoryName(), false,tabBean.getData().get(position).getChildren().get(i).getCategoryId()));
                    }
                }
                mTabAdapter.notifyDataSetChanged();
                image = tabBean.getData().get(position).getAdList().get(0).getImg();
            } else {
                mBinding.categoryView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 二级菜单点击
     */
    @Override
    public void onItem(int pos) {
        sets = pos;
        for (int i = 0; i < mChildrenBeans.size(); i++) {
            mChildrenBeans.get(i).setTag(false);
        }
        mChildrenBeans.get(pos).setTag(true);
        mTabAdapter.notifyDataSetChanged();
        mBinding.recyclerView.scrollToPosition(0);
        mBinding.swipeToLoad.setRefreshEnabled(true);
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
        mDialog.show();
        onRefresh();
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        if(mChildrenBeans.size() != 0){
            mPresenter.goodRank(mChildrenBeans.get(sets).getCategoryid(),this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
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
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }


    @Override
    public void onItemclick(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mCommenBeans.get(pos).getGoodsId())
            );
        }
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(HomeCommenBean commenBean) {
        if(commenBean.getCategory2Id().equals(mChildrenBeans.get(sets).getCategoryid())){
            if(commenBean.getData() != null  && commenBean.getData().size() != 0){
                mCommenBeans.clear();
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mCommenBeans.addAll(commenBean.getData());
                mAdapter.setImage(image);
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
            stopResher();
            if (once[0]){
                once[0] = false;
            }
        }
        stopResher();
    }

    /**
     * 失败
     */
    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show("网络出错");
    }
}
