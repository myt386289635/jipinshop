package com.example.administrator.jipinshop.activity.balance.boundalipay;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ActivityBoundalipayBinding;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe 绑定支付宝页面
 *
 * //        try {
//            String aes = AESEncrypt.encrypt("moxiaoting&123456&wujianing","danjiguanjia1234","danjiguanjia1234");
//            Log.d("BoundAlipayActivity", aes);
//
//            //解密
//            String s = "JB8dr9lSSWiRgfD0PnkWkUVtHN+rob7mssl7KoCCJTw=";
//            String value =  AESEncrypt.decrypt("danjiguanjia1234","danjiguanjia1234", Base64.decode(s, Base64.DEFAULT));
//            Log.d("BoundAlipayActivity", value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
 *
 *
 */
public class BoundAlipayActivity extends BaseActivity implements BoundAlipayView, View.OnClickListener {

    @Inject
    BoundAlipayPresenter mPresenter;

    private ActivityBoundalipayBinding mBinding;
    private CountDownTimer mTimer;
    private Boolean timerEnd = false;

    private  Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_boundalipay);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");

        mBinding.inClude.titleTv.setText("收款账户");
        mBinding.loginButton.setEnabled(false);
        mBinding.loginGetCode.setEnabled(false);
        mPresenter.setView(this);
        mTimer = mPresenter.initTimer(this,mBinding.loginGetCode);
        mPresenter.initLoginButton(mBinding.boundName,mBinding.boundNumber,mBinding.loginButton);

        if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipAccount).trim())){
            mBinding.boundName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipAccount,""));
        }
        if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipName).trim())){
            mBinding.boundNumber.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipName,""));
        }

    }

    @Override
    protected void onDestroy() {
        if(mTimer != null){
            mTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                showKeyboard(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 倒计时结束
     */
    @Override
    public void timerEnd() {
        timerEnd = false;
    }

    /**
     * 绑定支付宝回调
     * @param bean
     */
    @Override
    public void success(SuccessBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if(bean.getCode() == 200){
            Toast.makeText(this, "绑定成功", Toast.LENGTH_SHORT).show();
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.alipAccount,mBinding.boundName.getText().toString());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.alipName,mBinding.boundNumber.getText().toString());
            setResult(200);
            finish();
        }else {
            Toast.makeText(this, bean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_getCode:
                if(TextUtils.isEmpty(mBinding.houndPhone.getText().toString())){
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!timerEnd){
                    if(mTimer != null){
                        mTimer.start();
                    }
                    timerEnd = true;
                }
                break;
            case R.id.login_button:
                if(mDialog != null){
                    mDialog.show();
                }
                mPresenter.upAlipayInfo(mBinding.boundName.getText().toString(),mBinding.boundNumber.getText().toString(),this.bindToLifecycle());
                break;
        }
    }
}
