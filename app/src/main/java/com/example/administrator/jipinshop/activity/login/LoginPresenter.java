package com.example.administrator.jipinshop.activity.login;

import com.example.administrator.jipinshop.bean.LoginBean;
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
