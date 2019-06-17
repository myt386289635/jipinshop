package com.example.administrator.jipinshop.activity.info.bind;

import android.app.Dialog;
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

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
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
    @BindView(R.id.login_qrcode)
    EditText mLoginQrcode;

    @Inject
    BindNumberPresenter mPresenter;

    private CountDownTimer mTimer;
    private Boolean[] timerEnd = {false};
    private Dialog mDialog;

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
        mTimer = mPresenter.initTimer(this, mLoginGetCode);
        mPresenter.initLoginButton(mLoginNumber, mLoginCode, mLoginButton, mLoginGetCode, timerEnd);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
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
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                String invitationCode = "";
                if (!TextUtils.isEmpty(mLoginQrcode.getText().toString())){
                    invitationCode = mLoginQrcode.getText().toString();
                }
                mPresenter.Login(getIntent().getStringExtra("channel"), getIntent().getStringExtra("openid"),
                        mLoginNumber.getText().toString(), mLoginCode.getText().toString(),
                        invitationCode, this.<LoginBean>bindToLifecycle());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mButterKnife.unbind();
        super.onDestroy();
    }

    @Override
    public void timerEnd() {
        timerEnd[0] = false;
        if (mLoginNumber.getText().toString().length() == 11) {
            mLoginGetCode.setEnabled(true);
            mLoginGetCode.setBackgroundResource(R.drawable.bg_login2);
        } else {
            mLoginGetCode.setEnabled(false);
            mLoginGetCode.setBackgroundResource(R.drawable.bg_login);
        }
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (loginBean.getCode() == 0) {
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userAcutalName, loginBean.getData().getRealname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday, loginBean.getData().getBirthday());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender, loginBean.getData().getGender());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, loginBean.getData().getRole() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg, loginBean.getData().getAvatar());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName, loginBean.getData().getNickname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone, loginBean.getData().getMobile());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.token, loginBean.getData().getToken());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, loginBean.getData().getPoint());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindMobile, loginBean.getData().getBindMobile() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeibo, loginBean.getData().getBindWeibo() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin, loginBean.getData().getBindWeixin() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.qrCode, loginBean.getData().getInvitationCode());

            EventBus.getDefault().post(new EditNameBus(LoginActivity.tag, loginBean.getData().getFansCount() + ""
                    , loginBean.getData().getVoteCount() + "", loginBean.getData().getFollowCount() + ""));//刷新登陆后我的页面
            EventBus.getDefault().post(JPushReceiver.TAG);//刷新未读消息
            EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表
//            JPushInterface.resumePush(MyApplication.getInstance());//恢复推送
            if (loginBean.getData().getAddPoint() != 0) {
                EventBus.getDefault().post(new HomeNewPeopleBus(loginBean.getData().getAddPoint()));//新用户注册
            }
            ToastUtil.show("登录成功");
            setResult(222);
            finish();
        } else {
            ToastUtil.show(loginBean.getMsg());
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

    public void send(String ticket,String randstr){
        if (!timerEnd[0]) {
            if (mTimer != null) {
                mTimer.start();
            }
            timerEnd[0] = true;
        }
        mPresenter.pushMessage(mLoginNumber.getText().toString(),ticket,randstr, this.<SuccessBean>bindToLifecycle());
    }
}
