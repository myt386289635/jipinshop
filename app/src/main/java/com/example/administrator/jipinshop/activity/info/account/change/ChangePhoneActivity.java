package com.example.administrator.jipinshop.activity.info.account.change;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/2/15
 * @Describe 更换手机号
 */
public class ChangePhoneActivity extends BaseActivity implements ChangePhoneView {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.change_oldNum)
    TextView mChangeOldNum;
    @BindView(R.id.login_code)
    EditText mLoginCode;
    @BindView(R.id.login_getCode)
    TextView mLoginGetCode;
    @BindView(R.id.login_button)
    Button mLoginButton;

    @Inject
    ChangePhonePersenter mPersenter;
    private CountDownTimer mTimer;
    private Boolean[] timerEnd = {false};
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephone);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("更换手机号");
        mLoginButton.setEnabled(false);
        mLoginGetCode.setEnabled(true);
        mChangeOldNum.setText(FileManager.editPhone(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone)));
        mPersenter.setView(this);
        mPersenter.initLoginButton(mLoginCode,mLoginButton);
        mTimer = mPersenter.initTimer(this, mLoginGetCode);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
    }

    @OnClick({R.id.title_back, R.id.login_getCode, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_getCode:
                if (!timerEnd[0]) {
                    if (mTimer != null) {
                        mTimer.start();
                    }
                    mLoginGetCode.setEnabled(false);
                    timerEnd[0] = true;
                }
                mPersenter.pushMessage(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone),this.bindToLifecycle());
                break;
            case R.id.login_button:
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                mPersenter.validateMobileCode(mLoginCode.getText().toString(),this.bindToLifecycle());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        if (mTimer != null) {
            mTimer.cancel();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void timerEnd() {
        timerEnd[0] = false;
        mLoginGetCode.setEnabled(true);
    }

    @Override
    public void onSuccess(SuccessBean successBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (successBean.getCode() == 0){
            //下一步
            startActivity(new Intent(this, ChangePhone2Activity.class));
        }else {
            ToastUtil.show(successBean.getMsg());
        }
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
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

    @Subscribe
    public void eidtInfo(EditNameBus bus){
        if(bus.getTag().equals(ChangePhone2Activity.tag)){
           finish();
        }
    }
}
