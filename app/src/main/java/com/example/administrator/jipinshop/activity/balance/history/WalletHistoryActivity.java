package com.example.administrator.jipinshop.activity.balance.history;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.balance.detail.WalletDetailActivity;
import com.example.administrator.jipinshop.adapter.WalletHistoryAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.WalletHistoryBean;
import com.example.administrator.jipinshop.databinding.ActivityWalletHistoryBinding;
import com.example.administrator.jipinshop.util.PickerUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe 历史概况
 */
public class WalletHistoryActivity extends BaseActivity implements View.OnClickListener,OnRefreshListener, WalletHistoryAdapter.OnItem, WalletHistoryView {

    @Inject
    WalletHistoryPresenter mPresenter;
    private ActivityWalletHistoryBinding mBinding;
    private PickerUtil mPickerUtil; //时间选择器，最新选择器
    private List<WalletHistoryBean.DataBean> mList;
    private WalletHistoryAdapter mAdapter;
    private Dialog mDialog;

    private String type = "1"; // 1日历史概况，2月历史概况
    private String orderTime = "";//时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_wallet_history);
        mBinding.setListener(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.inClude.titleTv.setText("历史概况");
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);

        //时间选择器
        mPickerUtil = new PickerUtil();

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new WalletHistoryAdapter(mList,this);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> {
            mBinding.swipeToLoad.setRefreshing(true);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.history_time:
                //选择时间
                mPickerUtil.showPiker();
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getCommssionViewList(type,orderTime,this.bindToLifecycle());
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
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    //进入详情
    @Override
    public void onItemClick(int position) {
        String orderTime = "";
        if (mList.get(position).getDay() == 0){
            orderTime = mList.get(position).getYear() + "-" + mList.get(position).getMonth();
        }else {
            orderTime = mList.get(position).getYear() + "-" + mList.get(position).getMonth() + "-" + mList.get(position).getDay() ;
        }
        startActivity(new Intent(this, WalletDetailActivity.class)
                .putExtra("type", type)
                .putExtra("orderTime", orderTime)
        );
    }

    @Override
    public void onSuccess(WalletHistoryBean bean) {
        dissRefresh();
        Calendar startDate =  Calendar.getInstance();
        startDate.set(bean.getYear(),bean.getMonth() - 1,bean.getDay());
        if (type.equals("1")){
            //日选择器
            mPickerUtil.initDayTime(this, startDate, date -> {
                orderTime = date;
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
            });
        }else {
            //月选择器
            mPickerUtil.initMonthTime(this, startDate, date -> {
                orderTime = date;
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
            });
        }
        mList.clear();
        mList.addAll(bean.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String error) {
        dissRefresh();
        ToastUtil.show(error);
    }
}
