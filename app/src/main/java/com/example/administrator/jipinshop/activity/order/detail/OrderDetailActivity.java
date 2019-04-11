package com.example.administrator.jipinshop.activity.order.detail;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.databinding.ActivityOrderDetailBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/4/10
 * @Describe 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, OrderDetailView {

    @Inject
    OrderDetailPresenter mPresenter;

    private ActivityOrderDetailBinding mBinding;
    private MyOrderBean.DataBean mBean;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_detail);
        mBinding.setListener(this);
        mBean = (MyOrderBean.DataBean) getIntent().getSerializableExtra("date");
        mBinding.setDate(mBean);
        mBinding.inClude.titleTv.setText("订单详情");
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_buttom:
                if (mBinding.detailButtom.getText().toString().equals(getResources().getString(R.string.order_sure))){
                    //确认收货
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    mDialog.show();
                    mPresenter.orderConfirm(mBean.getId(),this.bindToLifecycle());
                }
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.detail_copy:
                //复制
                if (TextUtils.isEmpty(mBinding.detailExpresssn.getText().toString())){
                    ToastUtil.show("暂无快递单号");
                    return;
                }
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", mBinding.detailExpresssn.getText().toString());
                clip.setPrimaryClip(clipData);
                ToastUtil.show("复制成功");
                break;
        }
    }

    @Override
    public void SuccessConfirm() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        Intent intent = new Intent();
        intent.putExtra("pos",getIntent().getIntExtra("pos",-1));
        setResult(201,intent);
        finish();
    }

    @Override
    public void FaileConfirm(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
