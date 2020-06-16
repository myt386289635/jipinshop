package com.example.administrator.jipinshop.activity.balance.detail;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommssionDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityWalletDetailBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe 收益详情
 */
public class WalletDetailActivity extends BaseActivity implements View.OnClickListener, WalletDetailView {

    @Inject
    WalletDetailPresenter mPresenter;

    private ActivityWalletDetailBinding mBinding;
    private String type = "1"; // 1表示日收益详情，2表示月收益详情
    private String orderTime = "";//月收益详情日期格式：2020-06，日收益日期格式：2020-02-12
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_wallet_detail);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBaseActivityComponent.inject(this);
        mBinding.inClude.titleTv.setText("收益详情");
        mPresenter.setView(this);

        type = getIntent().getStringExtra("type");
        orderTime = getIntent().getStringExtra("orderTime");
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.getCommssionDetail2(orderTime,type,this.bindToLifecycle());
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
    public void onSuccess(CommssionDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mBinding.setDate(bean.getData());
        mBinding.executePendingBindings();
        if (bean.getData().getDay() == 0){
            mBinding.detailTime.setText(bean.getData().getYear() + "年" + bean.getData().getMonth() + "月");
        }else {
            mBinding.detailTime.setText(bean.getData().getYear() + "年" + bean.getData().getMonth() + "月" + bean.getData().getDay() + "日");
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
