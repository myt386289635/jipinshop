package com.example.administrator.jipinshop.activity.integral.detail;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.IntegralDetailAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityIntegralDetailBinding;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 积分明细
 */
public class IntegralDetailActivity extends BaseActivity implements IntegralDetailView, OnRefreshListener, View.OnClickListener {
    @Inject
    IntegralDetailPresenter mPresenter;

    private ActivityIntegralDetailBinding mBinding;
    private List<PointDetailBean.PointDetailListBean> mList;
    private IntegralDetailAdapter mAdapter;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_integral_detail);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        mBinding.inClude.titleTv.setText("积分明细");

        mList = new ArrayList<>();
        mAdapter = new IntegralDetailAdapter(this, mList);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(PointDetailBean bean) {
        if(mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()){
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        if (bean.getPointDetailList() != null && bean.getPointDetailList().size() != 0) {
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(bean.getPointDetailList());
            mAdapter.notifyDataSetChanged();
        } else {
            initError(R.mipmap.qs_integral, "暂无积分明细", "可以去签到领积分哦");
        }
    }

    /**
     * 失败
     */
    @Override
    public void onFile(String error) {
        if(mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()){
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onRefresh() {
        mPresenter.getDate(this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.net_clude:
                if(mBinding.netClude.errorTitle.getText().toString().equals("网络出错")){
                    dialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                    dialog.show();
                    onRefresh();
                }
                break;
        }
    }
}
