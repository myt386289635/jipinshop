package com.example.administrator.jipinshop.activity.info.account.change;

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
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/2/15
 * @Describe
 */
public class ChangePhonePersenter {

    private Repository mRepository;
    private ChangePhoneView mView;

    public void setView(ChangePhoneView view) {
        mView = view;
    }

    @Inject
    public ChangePhonePersenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 为了设置登陆按钮的背景颜色
     */
    public void initLoginButton(final EditText code, final Button loginButton) {
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(code.getText().toString())){
                        loginButton.setEnabled(false);
                        loginButton.setBackgroundResource(R.drawable.bg_login);
                } else {
                        loginButton.setEnabled(true);
                        loginButton.setBackgroundResource(R.drawable.bg_login2);
                }
            }
        });
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

    public void pushMessage(String mobile, String ticket, String randstr,LifecycleTransformer<SuccessBean> transformer){
        mRepository.pushMessage(mobile,"3",ticket,randstr)
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

    public void validateMobileCode(String code,LifecycleTransformer<SuccessBean> transformer){
        mRepository.validateMobileCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean ->{
                    if(mView != null){
                        mView.onSuccess(successBean);
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
