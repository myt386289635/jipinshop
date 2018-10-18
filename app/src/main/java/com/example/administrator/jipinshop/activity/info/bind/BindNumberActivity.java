package com.example.administrator.jipinshop.activity.info.bind;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 绑定手机号
 */
public class BindNumberActivity extends BaseActivity implements BindNumberView {


    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.login_numberImg)
    ImageView mLoginNumberImg;
    @BindView(R.id.login_number)
    EditText mLoginNumber;
    @BindView(R.id.login_numberLayout)
    RelativeLayout mLoginNumberLayout;
    @BindView(R.id.login_codeImg)
    ImageView mLoginCodeImg;
    @BindView(R.id.login_code)
    EditText mLoginCode;
    @BindView(R.id.login_getCode)
    TextView mLoginGetCode;
    @BindView(R.id.login_codeLayout)
    RelativeLayout mLoginCodeLayout;
    @BindView(R.id.login_button)
    Button mLoginButton;

    @Inject
    BindNumberPresenter mPresenter;

    private CountDownTimer mTimer;
    private Boolean timerEnd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_number);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("绑定手机");
        mLoginButton.setEnabled(false);
        mLoginGetCode.setEnabled(false);
        mPresenter.setView(this);
        mTimer = mPresenter.initTimer(this,mLoginGetCode);
        mPresenter.initLoginButton(mLoginNumber,mLoginCode,mLoginButton,mLoginGetCode);
    }

    @OnClick({R.id.title_back, R.id.login_getCode, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_getCode:
                if(TextUtils.isEmpty(mLoginNumber.getText().toString())){
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
                finish();
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
        timerEnd = false;
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
