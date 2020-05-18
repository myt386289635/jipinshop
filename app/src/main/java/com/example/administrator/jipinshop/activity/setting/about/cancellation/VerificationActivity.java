package com.example.administrator.jipinshop.activity.setting.about.cancellation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhone2Activity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.tencent.captchasdk.TCaptchaDialog;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2020/5/18
 * @Describe 验证页面
 */
public class VerificationActivity extends BaseActivity implements VerificationView {

    @Inject
    VerificationPresenter mPresenter;

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.login_number)
    EditText mLoginNumber;
    @BindView(R.id.login_code)
    EditText mLoginCode;
    @BindView(R.id.login_getCode)
    TextView mLoginGetCode;
    @BindView(R.id.login_button)
    Button mLoginButton;

    private CountDownTimer mTimer;
    private Boolean[] timerEnd = {false};
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephone2);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mLoginButton.setText("提交");
        mLoginButton.setBackgroundResource(R.drawable.bg_login2);

        mTitleTv.setText("验证手机号");
        mLoginGetCode.setEnabled(false);
        mTimer = mPresenter.initTimer(this,mLoginGetCode);
        mPresenter.initLoginButton(mLoginNumber,mLoginGetCode,timerEnd);
    }

    @OnClick({R.id.title_back, R.id.login_getCode, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_getCode:
                if (RetrofitModule.needVerify.equals("1")){//是否需要安全验证（0不需要，1需要）
                    TCaptchaDialog dialog = new TCaptchaDialog(this, true, dialog1 ->{
                        ToastUtil.show("验证取消");
                    },"2087266956", jsonObject -> {
                        try {
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) { //验证成功
                                String ticket = jsonObject.getString("ticket");
                                String randstr = jsonObject.getString("randstr");
                                send(ticket,randstr);
                            } else if (ret == -1001) {// 首个TCaptcha.js加载错误时允许用户(操作者)或业务方(接入方)介入重试
                                ToastUtil.show("验证码加载错误：" + jsonObject.toString());
                            } else { //用户(可能)点了验证码的关闭按钮
                                ToastUtil.show("验证失败：" +  jsonObject.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },null);
                    dialog.show();
                }else {
                    send("","");
                }
                break;
            case R.id.login_button:
                if (TextUtils.isEmpty(mLoginNumber.getText().toString())){
                    ToastUtil.show("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mLoginCode.getText().toString())){
                    ToastUtil.show("请输入短信验证码");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
                mDialog.show();
                mPresenter.destroyAccount(mLoginNumber.getText().toString(),mLoginCode.getText().toString(),this.bindToLifecycle());
                break;
        }
    }

    @Override
    public void timerEnd() {
        timerEnd[0] = false;
        if(mLoginNumber.getText().toString().length() == 11){
            mLoginGetCode.setEnabled(true);
            mLoginGetCode.setBackgroundResource(R.drawable.bg_login2);
        }else {
            mLoginGetCode.setEnabled(false);
            mLoginGetCode.setBackgroundResource(R.drawable.bg_login);
        }
    }

    @Override
    public void onSuccess(SuccessBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if(bean.getCode() == 0){
            startActivity(new Intent(this,CancelResultActivity.class));
            setResult(201);
            finish();
        }else {
            ToastUtil.show(bean.getMsg());
        }
    }

    @Override
    protected void onDestroy() {
        if(mTimer != null){
            mTimer.cancel();
        }
        mButterKnife.unbind();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hideSoftInput(view);
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

    private void hideSoftInput(View view) {
        if (mImm.isActive()) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public void send(String ticket,String randstr){
        if(!timerEnd[0]){
            if(mTimer != null){
                mTimer.start();
            }
            timerEnd[0] = true;
        }
        mLoginGetCode.setEnabled(false);//发送后不能再点击
        mPresenter.pushMessage(mLoginNumber.getText().toString(),ticket,randstr,this.<SuccessBean>bindToLifecycle());
    }

}
