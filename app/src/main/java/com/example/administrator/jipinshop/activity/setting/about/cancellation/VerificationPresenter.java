package com.example.administrator.jipinshop.activity.setting.about.cancellation;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
 * @create 2020/5/18
 * @Describe
 */
public class VerificationPresenter {

    private Repository mRepository;
    private VerificationView mView;

    public void setView(VerificationView view) {
        mView = view;
    }

    @Inject
    public VerificationPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 验证码倒计时逻辑代码
     */
    public CountDownTimer initTimer(final Context context, final TextView textView) {
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished / 1000 + "s");
                textView.setTextColor(context.getResources().getColor(R.color.color_white));
                textView.setBackgroundResource(R.drawable.bg_timecounter);
            }

            @Override
            public void onFinish() {
                textView.setText("获取验证码");
                textView.setTextColor(context.getResources().getColor(R.color.color_white));
                textView.setBackgroundResource(R.drawable.bg_login);
                mView.timerEnd();
            }
        };
        return timer;
    }

    /**
     * 为了设置验证码背景
     */
    public void initLoginButton(final EditText number, final TextView mLoginGetCode, Boolean[] timerEnd) {
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
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
    }

    public void pushMessage(String mobile, String ticket, String randstr, LifecycleTransformer<SuccessBean> transformer){
        mRepository.pushMessage(mobile,"4",ticket,randstr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean ->{
                    if(successBean.getCode() != 0){
                        ToastUtil.show(successBean.getMsg());
                    }else {
                        ToastUtil.show("验证码已发送，请注意查收");
                    }
                }, throwable -> {
                    ToastUtil.show(throwable.getMessage());
                });
    }

    public void destroyAccount(String mobile, String code , LifecycleTransformer<SuccessBean> transformer){
        mRepository.destroyAccount(mobile, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (mView != null){
                        mView.onSuccess(successBean);
                    }
                }, throwable -> {
                    Log.d("LoginPresenter", throwable.getMessage());
                });
    }
}
