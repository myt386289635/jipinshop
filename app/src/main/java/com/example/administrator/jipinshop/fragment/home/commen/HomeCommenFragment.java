package com.example.administrator.jipinshop.fragment.home.commen;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.adapter.HomeCommenAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.OrderbyTypeBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.databinding.FragmentHomeCommenBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.fragment.home.commen.tab.HomeCommenTabFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/4
 * @Describe 榜单的公共类
 */
public class HomeCommenFragment extends DBBaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener, HomeCommenView, OnRefreshListener, HomeCommenAdapter.OnItem {

    @Inject
    HomeCommenPresenter mPresenter;

    private FragmentHomeCommenBinding mBinding;
    private int position;//记录这是第几个fragment; 从1开始
    private String category2Id = "";
    private String orderbyType = "1";
    private List<Fragment> tabFragments;
    private HomeAdapter mPagerAdapter;
    private List<ImageView> point;
    private List<TextView> mTabTextView;//记录热卖榜等4个榜的title
    private List<View> mTabLine;//记录热卖榜等4个榜的line

    private Dialog mDialog;//点击二级菜单时请求数据加载框
    private Boolean[] once = {true};//记录第一次进入页面标示

    private HomeCommenAdapter mAdapter;
    private List<HomeCommenBean.DataBean> mCommenBeans;
    private int[] set = {0};//记录点击热卖榜4个榜的哪个位置；

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once[0]) {
            orderbyType = "1";
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
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        position = getArguments().getInt("set",0);
        tabFragments = new ArrayList<>();
        point = new ArrayList<>();
        mPagerAdapter = new HomeAdapter(getChildFragmentManager());
        mPagerAdapter.setFragments(tabFragments);
        mBinding.tabViewpager.setAdapter(mPagerAdapter);
        mBinding.tabViewpager.addOnPageChangeListener(this);

        mTabTextView = new ArrayList<>();
        mTabLine = new ArrayList<>();
        mTabTextView.add(mBinding.tab1.tabText);
        mTabTextView.add(mBinding.tab2.tabText);
        mTabTextView.add(mBinding.tab3.tabText);
        mTabTextView.add(mBinding.tab4.tabText);
        mTabLine.add(mBinding.tab1.tabLine);
        mTabLine.add(mBinding.tab2.tabLine);
        mTabLine.add(mBinding.tab3.tabLine);
        mTabLine.add(mBinding.tab4.tabLine);
        mPresenter.initTabLayout(getContext(),mBinding);
        mPresenter.orderbyTypeList(this.bindToLifecycle());

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommenBeans = new ArrayList<>();
        mAdapter = new HomeCommenAdapter(mCommenBeans,getContext());
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(getContext(),mBinding.recyclerView,mBinding.swipeToLoad,mBinding.appbar,once,mBinding,mTabLine,set);
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
                mBinding.coordinator.setVisibility(View.VISIBLE);
                TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);
                if(tabBean.getData().get(position).getChildren().size() != 0){
                    if (tabBean.getData().get(position).getChildren().size() <= 10) {
                        tabFragments.add(HomeCommenTabFragment.getInstance(0,position));
                        mBinding.point.setVisibility(View.GONE);
                    }else {
                        mBinding.point.setVisibility(View.VISIBLE);
                        for (int i = 0; i < tabBean.getData().get(position).getChildren().size(); i = i + 10) {
                            tabFragments.add(HomeCommenTabFragment.getInstance(i/10,position));
                        }
                        mPresenter.initBanner(tabFragments,getContext(),point,mBinding.point);
                    }
                    mPagerAdapter.notifyDataSetChanged();
                    category2Id = tabBean.getData().get(position).getChildren().get(0).getCategoryId();
                }else {
                    mBinding.coordinator.setVisibility(View.GONE);
                    category2Id = "";
                }
            } else{
                mBinding.coordinator.setVisibility(View.GONE);
                category2Id = "";
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < point.size(); i++) {
            if (i == position % tabFragments.size()){
                point.get(i).setImageResource(R.drawable.banner_down2);
            }else {
                point.get(i).setImageResource(R.drawable.banner_up2);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab1:
                set[0] = 0;
                mPresenter.seleteTab(getContext(),set[0],mTabTextView,mTabLine);
                orderbyType="1";
                mBinding.recyclerView.scrollToPosition(0);
                mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
                mDialog.show();
                onRefresh();
                break;
            case R.id.tab2:
                set[0] = 1;
                mPresenter.seleteTab(getContext(),set[0],mTabTextView,mTabLine);
                orderbyType="2";
                mBinding.recyclerView.scrollToPosition(0);
                mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
                mDialog.show();
                onRefresh();
                break;
            case R.id.tab3:
                set[0] = 2;
                mPresenter.seleteTab(getContext(),set[0],mTabTextView,mTabLine);
                orderbyType="3";
                mBinding.recyclerView.scrollToPosition(0);
                mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
                mDialog.show();
                onRefresh();
                break;
            case R.id.tab4:
                set[0] = 3;
                mPresenter.seleteTab(getContext(),set[0],mTabTextView,mTabLine);
                orderbyType="4";
                mBinding.recyclerView.scrollToPosition(0);
                mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
                mDialog.show();
                onRefresh();
                break;
        }
    }

    @Override
    public void onSuccess(HomeCommenBean commenBean) {
        if(commenBean.getCategory2Id().equals(category2Id)){
            if(commenBean.getData() != null  && commenBean.getData().size() != 0){
                mCommenBeans.clear();
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.nestedScrollview.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mCommenBeans.addAll(commenBean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            }
            stopResher();
            if (once[0]){
                once[0] = false;
            }
        }
        stopResher();
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        ToastUtil.show("网络出错");
        if (once[0]){
            once[0] = false;
        }
    }

    @Override
    public void onSuccessTab(OrderbyTypeBean commenBean) {
        mBinding.tab1.tabText.setText(commenBean.getData().get(0).getName());
        mBinding.tab2.tabText.setText(commenBean.getData().get(1).getName());
        mBinding.tab3.tabText.setText(commenBean.getData().get(2).getName());
        mBinding.tab4.tabText.setText(commenBean.getData().get(3).getName());
    }

    @Override
    public void onFileTab(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onRefresh() {
        if(!TextUtils.isEmpty(category2Id)){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.coordinator.setVisibility(View.VISIBLE);
            mPresenter.goodRank(category2Id,orderbyType,this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }else {
            stopResher();
            initErrorPage(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            ToastUtil.show("网络请求错误,请重新开启app");
        }
    }

    public void initErrorPage(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.coordinator.setVisibility(View.GONE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.nestedScrollview.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
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
            BigDecimal bigDecimal = new BigDecimal(mCommenBeans.get(pos).getPv());
            mCommenBeans.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mCommenBeans.get(pos).getGoodsId())
            );
        }
    }

    /**
     * 二级菜单点击
     */
    public void onItemTab(String category2Id){
        this.category2Id = category2Id;
        mBinding.recyclerView.scrollToPosition(0);
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
        mDialog.show();
        onRefresh();
    }
}
