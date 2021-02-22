package com.example.administrator.jipinshop.activity.home.seckill;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.seckill.detail.SeckillDetailActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.adapter.KTTabAdapter6;
import com.example.administrator.jipinshop.adapter.SeckillAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SeckillBean;
import com.example.administrator.jipinshop.bean.SeckillTabBean;
import com.example.administrator.jipinshop.databinding.ActivitySeckillBinding;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/12/30
 * @Describe 秒杀主页(变成fragment了)
 */
public class SeckillFragment extends DBBaseFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, SeckillView, KTTabAdapter6.OnClickItem, SeckillAdapter.OnItem {

    @Inject
    SeckillPresenter mPresenter;

    private ActivitySeckillBinding mBinding;
    private List<SeckillTabBean.DataBean> tabs;
    private KTTabAdapter6 mTabAdapter;
    private int index = 0; //选中的位置
    private Dialog mDialog;
    private int page = 1;
    private Boolean refersh = true;
    private List<SeckillBean.DataBean> mList;
    private SeckillAdapter mAdapter;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.activity_seckill,container,false);
        mBinding.setListener(this);
        mBaseFragmentComponent.inject(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mPresenter.setView(this);

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        tabs = new ArrayList<>();
        mTabAdapter = new KTTabAdapter6(tabs,this);
        commonNavigator.setAdapter(mTabAdapter);
        mBinding.seckillMenu.setNavigator(commonNavigator);

        mList = new ArrayList<>();
        mAdapter = new SeckillAdapter(getContext(),mList);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.categoryList(this.bindToLifecycle());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                if (getActivity() != null)
                    getActivity().finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.seckillList(tabs.get(index).getId(),page,this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.seckillList(tabs.get(index).getId(),page,this.bindToLifecycle());
    }

    @Override
    public void onTab(SeckillTabBean bean) {
        tabs.clear();
        tabs.addAll(bean.getData());
        mTabAdapter.notifyDataSetChanged();
        for (int i = 0; i < bean.getData().size(); i++) {
            if (bean.getData().get(i).getStatus() == 2){
                index = i;
                break;
            }
        }
        mBinding.seckillMenu.onPageSelected(index);
        mBinding.seckillMenu.onPageScrolled(index,0.0F, 0);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSuccess(SeckillBean bean) {
        if (refersh){
            dissRefresh();
            if (bean.getData().size() != 0){
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
        }else {
            dissLoading();
            if (bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    @Override
    public void onContentFile(String error) {
        if(refersh){
            dissRefresh();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            dissLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    //选中刷新页面
    @Override
    public void onSelectMenu(int index) {
        this.index = index;
        mBinding.seckillMenu.onPageSelected(index);
        mBinding.seckillMenu.onPageScrolled(index,0.0F, 0);
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        onRefresh();
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    public void dissLoading(){
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
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onItemShare(int position) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (TextUtils.isEmpty(mList.get(position).getSource()) || mList.get(position).getSource().equals("2")){
            TaoBaoUtil.openTB(getContext(), () -> {
                startActivity(new Intent(getContext(), ShareActivity.class)
                        .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                        .putExtra("source",mList.get(position).getSource())
                );
            });
        }else{
            startActivity(new Intent(getContext(), ShareActivity.class)
                    .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                    .putExtra("source",mList.get(position).getSource())
            );
        }
    }

    @Override
    public void onDetail(int position) {
        startActivity(new Intent(getContext(), SeckillDetailActivity.class)
                .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                .putExtra("source", mList.get(position).getSource())
                .putExtra("id",mList.get(position).getId())
        );
    }
}
