package com.example.administrator.jipinshop.activity.mall.order;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.mall.order.detail.OrderDetailActivity;
import com.example.administrator.jipinshop.adapter.MyOrderAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe 兑换记录页面
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, MyOrderView, MyOrderAdapter.OnClickItem {

    private ActivityMessageSystemBinding mBinding;

    @Inject
    MyOrderPresenter mPresenter;
    private MyOrderAdapter mAdapter;
    private List<MyOrderBean.DataBean> mList;

    /**
     * 页数
     */
    private int page = 1;
    /**
     * 记录是刷新还是加载
     */
    private Boolean refersh = true;

    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_message_system);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("兑换记录");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new MyOrderAdapter(this, mList);
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.orderList(page+"",this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.orderList(page+"",this.bindToLifecycle());
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
        if(mDialog != null && mDialog.isShowing()){
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
    public void Success(MyOrderBean myOrderBean) {
        if(refersh){
            dissRefresh();
        }else {
            dissLoading();
        }
        if (myOrderBean.getData() != null && myOrderBean.getData().size() != 0){
            //有数据
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            if(refersh){
                mList.clear();
            }
            mList.addAll(myOrderBean.getData());
            mAdapter.notifyDataSetChanged();
            if(!refersh){
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }
        }else {
            //没有数据
            if(refersh){
                //刷新时
                initError(R.mipmap.qs_order, "暂无订单", "还没有任何订单哦，先逛一下吧");
                mBinding.recyclerView.setVisibility(View.GONE);
            }else {
                //加载时
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    @Override
    public void Faile(String error) {
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

    @Override
    public void SuccessConfirm(int position) {
        dissRefresh();
        mList.get(position).setStatus(3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        mList.get(position).setFinishTime(simpleDateFormat.format(date));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void FaileConfirm(String error) {
        ToastUtil.show(error);
        dissRefresh();
    }

    @Override
    public void onClickItem(int position) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.orderConfirm(position,mList.get(position).getId(),this.bindToLifecycle());
    }

    /**
     * 跳转到订单详情页面
     */
    @Override
    public void onClickDetailItem(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivityForResult(new Intent(this, OrderDetailActivity.class)
                    .putExtra("date",mList.get(position))
                    .putExtra("pos",position)
            ,201);
        }
    }

    @Override
    public void onClickCopy(int position) {
        if (TextUtils.isEmpty(mList.get(position).getRemark())){
            ToastUtil.show("暂无激活码");
            return;
        }
        ClipboardManager clip1 = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData1 = ClipData.newPlainText("jipinshop", mList.get(position).getRemark());
        clip1.setPrimaryClip(clipData1);
        ToastUtil.show("复制成功");
        SPUtils.getInstance().put(CommonDate.CLIP,mList.get(position).getRemark());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 201:
                int position = data.getIntExtra("pos",-1);
                if (position != -1){
                    mList.get(position).setStatus(3);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
                    mList.get(position).setFinishTime(simpleDateFormat.format(date));
                    mAdapter.notifyDataSetChanged();
                }else {
                    if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                        mBinding.swipeToLoad.setRefreshEnabled(true);
                        mBinding.swipeToLoad.setRefreshing(true);
                        mBinding.swipeToLoad.setRefreshEnabled(false);
                    } else {
                        mBinding.swipeToLoad.setRefreshing(true);
                    }
                }
                break;
        }
    }
}
