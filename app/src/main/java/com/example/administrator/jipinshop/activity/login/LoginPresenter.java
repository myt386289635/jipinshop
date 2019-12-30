package com.example.administrator.jipinshop.activity.login;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
@SuppressWarnings("all")
public class LoginPresenter {

    Repository mRepository;
    private LoginView mView;

    public void setView(LoginView view) {
        mView = view;
    }

    @Inject
    public LoginPresenter(Repository repository) {
        mRepository = repository;
    }


    /**
     * 验证码倒计时逻辑代码
     */
    public CountDownTimer initTimer(final Context context, final TextView textView) {
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished / 1000 + "s后重发 ");
                textView.setTextColor(context.getResources().getColor(R.color.color_white));
                textView.setBackgroundResource(R.drawable.bg_timecounter);
            }

            @Override
            public void onFinish() {
                textView.setText("获取验证码");
                textView.setTextColor(context.getResources().getColor(R.color.color_white));
                mView.timerEnd();
            }
        };
        return timer;
    }

    /**
     * 为了设置登陆按钮的背景颜色
     */
    public void initLoginButton(final EditText number, final EditText code, final Button loginButton, final TextView mLoginGetCode,Boolean[] timerEnd) {
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(code.getText().toString())) {
                    if (TextUtils.isEmpty(number.getText().toString())){
                        loginButton.setEnabled(false);
                        loginButton.setBackgroundResource(R.drawable.bg_login);
                    } else {
                        loginButton.setEnabled(true);
                        loginButton.setBackgroundResource(R.drawable.bg_login2);
                    }
                } else {
                    loginButton.setEnabled(false);
                    loginButton.setBackgroundResource(R.drawable.bg_login);
                }

                if(!timerEnd[0]){
                    if(number.getText().length() == 11){
                        mLoginGetCode.setEnabled(true);
                        mLoginGetCode.setBackgroundResource(R.drawable.bg_login2);
                    }else {
                        mLoginGetCode.setEnabled(false);
                        mLoginGetCode.setBackgroundResource(R.drawable.bg_login);
                    }
                }

            }
        });

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(number.getText().toString())) {
                    if (TextUtils.isEmpty(code.getText().toString())){
                        loginButton.setEnabled(false);
                        loginButton.setBackgroundResource(R.drawable.bg_login);
                    } else {
                        loginButton.setEnabled(true);
                        loginButton.setBackgroundResource(R.drawable.bg_login2);
                    }
                } else {
                    loginButton.setEnabled(false);
                    loginButton.setBackgroundResource(R.drawable.bg_login);
                }
            }
        });

    }


    public void login(String mobile ,String code , String invitationCode,LifecycleTransformer<LoginBean> transformer){
        mRepository.login(mobile,code,invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(s -> {
                   if (mView != null){
                       mView.loginSuccess(s);
                   }
                }, throwable -> {
                    if (!TextUtils.isEmpty(throwable.getMessage()))
                        Log.d("LoginPresenter", throwable.getMessage());
                });
    }


    public void pushMessage(Context context,String mobile, String ticket, String randstr,LifecycleTransformer<SuccessBean> transformer){
        mRepository.pushMessage(mobile,"1",ticket,randstr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean ->{
                    if(successBean.getCode() != 0){
                       ToastUtil.show(successBean.getMsg());
                    }else {
                        ToastUtil.show("验证码已发送，请注意查收");
                    }
                    Log.d("LoginPresenter", successBean.toString());
                }, throwable -> {
                    Log.d("LoginPresenter", throwable.getMessage());
                });
    }

    public void thirdLogin(String accessToken, String openid,String channel,LifecycleTransformer<LoginBean> transformer){
        mRepository.thirdLogin(accessToken,openid,channel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(userInfoBean -> {
                    if (mView != null){
                        mView.loginWx(userInfoBean,channel,openid);
                    }
                }, throwable -> {
                    ToastUtil.show("授权登陆失败，请检查网络");
                });
    }

}
