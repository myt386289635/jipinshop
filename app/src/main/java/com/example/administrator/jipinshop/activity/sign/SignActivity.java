package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.integral.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 签到页面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener, SignView {

    public static final String eventbusTag = "SignActivity";

    @Inject
    SignPresenter mPresenter;

    private ActivitySignBinding mBinding;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setSignView(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,this);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.signInfo(this.bindToLifecycle());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.sign_rule:
                //积分规则
                break;
            case R.id.sign_signed:
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.sign(this.bindToLifecycle());
                break;
            case R.id.sign_detail:
                //积分明细
                startActivity(new Intent(this, IntegralDetailActivity.class));
                break;
        }
    }

    /**
     * 获取签到信息成功回调
     */
    @Override
    public void getInfoSuc(SignBean signBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        // TODO: 2019/1/16
    }
    /**
     * 获取签到信息失败回调
     */
    @Override
    public void getInfoFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 签到成功
     */
    @Override
    public void signSuc(SignInsertBean signInsertBean) {
        EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        // TODO: 2019/1/16
    }
    /**
     * 签到失败
     */
    @Override
    public void signFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
