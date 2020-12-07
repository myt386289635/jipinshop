package com.example.administrator.jipinshop.activity.login;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
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

    /**
     * 一键登录
     */
    public void jVerifyLogin(String loginToken,LifecycleTransformer<LoginBean> transformer){
        mRepository.JVerifyLogin(loginToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        ToastUtil.show(bean.getMsg());
                    }
                }, throwable -> {
                    ToastUtil.show(throwable.getMessage());
                });
    }

    /**
     * 一键绑定
     */
    public void jVerifyBind(String loginToken , String openid , LifecycleTransformer<LoginBean> transformer){
        mRepository.JVerifyBind(loginToken, openid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(loginBean -> {
                    if (loginBean.getCode() == 0){
                        mView.onSuccess(loginBean);
                    }else {
                        ToastUtil.show(loginBean.getMsg());
                    }
                }, throwable -> {
                    ToastUtil.show(throwable.getMessage());
                });
    }
}
