package com.example.administrator.jipinshop.activity.info.account.change;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.info.account.AccountManageActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/2/15
 * @Describe 更换手机号页面2
 */
public class ChangePhone2Activity extends BaseActivity implements ChangePhone2View {

    public static final String tag = "ChangePhone2Activity_ChangePhone";

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

    @Inject
    ChangePhone2Persenter mPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephone2);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("更换手机号");
        mLoginButton.setEnabled(false);
        mLoginGetCode.setEnabled(false);
        mPersenter.setView(this);
        mTimer = mPersenter.initTimer(this,mLoginGetCode);
        mPersenter.initLoginButton(mLoginNumber,mLoginCode,mLoginButton,mLoginGetCode,timerEnd);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
    }

    @OnClick({R.id.title_back, R.id.login_getCode, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_getCode:
                if(!timerEnd[0]){
                    if(mTimer != null){
                        mTimer.start();
                    }
                    timerEnd[0] = true;
                }
                mPersenter.pushMessage(mLoginNumber.getText().toString(),this.<SuccessBean>bindToLifecycle());
                break;
            case R.id.login_button:
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                mPersenter.changeMobile(mLoginNumber.getText().toString(),mLoginCode.getText().toString(),this.bindToLifecycle());
                break;
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
    public void onSuccess(SuccessBean successBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if(successBean.getCode() == 0){
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone,mLoginNumber.getText().toString());
            EventBus.getDefault().post(new EditNameBus(ChangePhone2Activity.tag));
            ToastUtil.show("更换手机号成功");
            finish();
        }else {
            ToastUtil.show(successBean.getMsg());
        }
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
}
