package com.example.administrator.jipinshop.fragment.home.household;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.HouseholdFragmentGridAdapter;
import com.example.administrator.jipinshop.adapter.HouseholdFragmentRecyclerAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;
import com.example.administrator.jipinshop.bean.HouseholdFragmentBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.databinding.FragmentHouseholdBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 生活家电
 */
public class HouseholdFragment extends DBBaseFragment implements HouseholdFragmentGridAdapter.OnItem, OnRefreshListener, HouseholdFragmentRecyclerAdapter.OnItem, HouseholdFragmentView {

    @Inject
    HouseholdFragmentPresenter mPresenter;

    private FragmentHouseholdBinding mBinding;
    private List<HealthFragmentGridBean> gridViewList;
    private HouseholdFragmentGridAdapter mAdapter;

    private HouseholdFragmentRecyclerAdapter mRecyclerAdapter;
    private List<HouseholdFragmentBean.ListBean> recyclerList;

    private Boolean once = true;

    private int position = 0;//二级导航点击的位置   默认为第0个  这个也是最后一次点击的位置
    private Dialog mDialog;//请求数据

    public static HouseholdFragment getInstance() {
        HouseholdFragment fragment = new HouseholdFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_household, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        gridViewList = new ArrayList<>();
        mAdapter = new HouseholdFragmentGridAdapter(gridViewList, getContext());
        mAdapter.setOnItem(this);
        mBinding.gridView.setAdapter(mAdapter);
        mBinding.gridView.setVisibility(View.GONE);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList = new ArrayList<>();
        mRecyclerAdapter = new HouseholdFragmentRecyclerAdapter(recyclerList, getContext());
        mRecyclerAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mRecyclerAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,getContext());
    }

    /**
     * 二级导航点击事件
     */
    @Override
    public void onItem(int pos) {
        position = pos;
        mPresenter.refreshGirdView(getContext(), mBinding.swipeToLoad, gridViewList, pos, mAdapter, mBinding.recyclerView);
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
        mDialog.show();
        onRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            if (gridViewList.size() != 0) {
                if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.HouseholdFragmentDATA,""))) {
                    initDate();
                }
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            }
        }
    }

    /**
     * 页面刷新
     */
    @Override
    public void onRefresh() {
        if(gridViewList.size() != 0){
            mPresenter.goodRank(gridViewList.get(position).getCategoryid(),this.bindToLifecycle());
        }else {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "网络请求错误,请重新开启app", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItemclick(int pos) {
        if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",recyclerList.get(pos).getGoodsId())
                    .putExtra("goodsName",recyclerList.get(pos).getGoodsName())
                    .putExtra("priceNow",recyclerList.get(pos).getActualPrice())
                    .putExtra("priceOld",recyclerList.get(pos).getOtherPrice())
                    .putExtra("price",recyclerList.get(pos).getCutPrice())
                    .putExtra("state",recyclerList.get(pos).getSourceStatus() + "")
                    .putExtra("goodsImage",recyclerList.get(pos).getRankGoodImg())
            );
        }
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
                mBinding.gridView.setVisibility(View.VISIBLE);
                TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);
                gridViewList.clear();
                for (int i = 0; i < tabBean.getList().get(3).getSubtilte().size(); i++) {
                    if (i == 0) {
                        gridViewList.add(new HealthFragmentGridBean(tabBean.getList().get(3).getSubtilte().get(i).getCategoryname(), true,tabBean.getList().get(3).getSubtilte().get(i).getCategoryid()));
                    } else {
                        gridViewList.add(new HealthFragmentGridBean(tabBean.getList().get(3).getSubtilte().get(i).getCategoryname(), false,tabBean.getList().get(3).getSubtilte().get(i).getCategoryid()));
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else {
                mBinding.gridView.setVisibility(View.GONE);
            }
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
    public void onSuccess(HouseholdFragmentBean householdFragmentBean) {
        if(householdFragmentBean.getCategory2Id().equals(gridViewList.get(position).getCategoryid())){
            if(position == 0){
                SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.HouseholdFragmentDATA,new Gson().toJson(householdFragmentBean));
            }
            stopResher();
            if(householdFragmentBean.getList() != null  && householdFragmentBean.getList().size() != 0){
                recyclerList.clear();
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                recyclerList.addAll(householdFragmentBean.getList());
                mRecyclerAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
//            Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
        stopResher();
    }

    @Override
    public void onFile(String error) {
        stopResher();
        if(position == 0 && !TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.HouseholdFragmentDATA,""))){
            initDate();
        }else {
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化本地数据
     */
    public void initDate(){
        HouseholdFragmentBean householdFragmentBean = new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.HouseholdFragmentDATA),HouseholdFragmentBean.class);
        recyclerList.clear();
        mBinding.inClude.qsNet.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        recyclerList.addAll(householdFragmentBean.getList());
        mRecyclerAdapter.notifyDataSetChanged();
    }
}
