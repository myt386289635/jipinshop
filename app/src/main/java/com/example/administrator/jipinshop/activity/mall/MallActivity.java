package com.example.administrator.jipinshop.activity.mall;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.activity.mall.order.MyOrderActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.adapter.MallAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.databinding.ActivityMallBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城
 */
public class MallActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, MallAdapter.OnItemListener, MallView {

    @Inject
    MallPresenter mPresenter;
    private ActivityMallBinding mBinding;

    private List<MallBean.DataBean> mList;
    private List<MallBean.DataBean> mHot;
    private MallAdapter mAdapter;
    private int page = 2;//页数
    private Boolean refersh = true;//记录是刷新还是加载
    private int from = 0;//1 赚钱页过来  0 其他页过来

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_mall);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        from = getIntent().getIntExtra("from",0);
        mBinding.inClude.titleTv.setText("极币商城");
        mBinding.swipeTarget.setLayoutManager(new GridLayoutManager(this, 2));
        mList = new ArrayList<>();
        mHot = new ArrayList<>();
        mAdapter = new MallAdapter(this, mList,mHot);
        mAdapter.setOnItemListener(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

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
            case R.id.title_right:
                startActivity(new Intent(this, MyOrderActivity.class));
                UAppUtil.mine(this,6);
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 2;
        refersh = true;
        mPresenter.hotList(this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.mallList(page,this.bindToLifecycle());
    }

    //更多兑换
    @Override
    public void onMoreDetail(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(this, MallDetailActivity.class)
                    .putExtra("goodsId",mList.get(position).getId())
                    .putExtra("isActivityGoods",mList.get(position).getType())
            );
        }
    }

    //热门兑换
    @Override
    public void onHotDetail(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(this, MallDetailActivity.class)
                    .putExtra("goodsId",mHot.get(position).getId())
                    .putExtra("isActivityGoods",mHot.get(position).getType())
            );
        }
    }

    @Override
    public void onHead() {
        startActivity(new Intent(this, SignActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //每日任务里点击跳转后，需要关闭的页面
    @Subscribe
    public void changePage(ChangeHomePageBus bus){
        if(bus != null){
            finish();
        }
    }

    @Override
    public void onSuccess(MallBean bean) {
        if (refersh){
            dissRefresh();
            if (bean.getData() != null && bean.getData().size() != 0){
                //有数据
                mList.clear();
                mList.addAll(bean.getData());
            }else {
                ToastUtil.show("暂无数据");
            }
            mAdapter.notifyDataSetChanged();//刷新热门兑换
        }else {
            dissLoading();
            if (bean.getData() != null && bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    @Override
    public void onFile(String error) {
        if(refersh){
            dissRefresh();
        }else {
            dissLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    @Override
    public void onHot(MallBean bean) {
        mHot.clear();
        mHot.addAll(bean.getData());
        mPresenter.mallList(page,this.bindToLifecycle());
    }

    @Override
    public void onHotFile(String error) {
        ToastUtil.show(error);
        mPresenter.mallList(page,this.bindToLifecycle());
    }
}
