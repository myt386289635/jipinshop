package com.example.administrator.jipinshop.activity.balance.record;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.RecordAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.databinding.ActivityRecordBinding;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe 提现金额
 */
public class RecordActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, RecordView {

    @Inject
    RecordPresenter mPresenter;

    private ActivityRecordBinding mBinding;
    private List<RecordBean.ListBean> mList;
    private RecordAdapter mAdapter;

    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_record);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("提现记录");
        mPresenter.setView(this);

        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new RecordAdapter(this,mList);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        mPresenter.getRecord(this.bindToLifecycle());
    }

    /**
     * 获取提现记录成功
     * @param recordBean
     */
    @Override
    public void SuccessRecord(RecordBean recordBean) {
        if(mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()){
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if(recordBean.getList() != null && recordBean.getList().size() != 0){
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(recordBean.getList());
            mAdapter.notifyDataSetChanged();
        }else {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            initError(R.mipmap.qs_integral, "暂无提现记录", "还没有任何提现记录哦");
        }
    }

    /**
     * 获取提现记录失败
     */
    @Override
    public void FaileRecord(String error) {
        if(mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()){
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }
}
