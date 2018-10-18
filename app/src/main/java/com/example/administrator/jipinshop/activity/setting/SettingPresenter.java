package com.example.administrator.jipinshop.activity.setting;

import android.app.Dialog;
import android.util.Log;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/9/29
 * @Describe
 */
public class SettingPresenter {

    Repository mRepository;
    private SettingView mView;

    public void setView(SettingView view) {
        mView = view;
    }

    @Inject
    public SettingPresenter(Repository repository) {
        mRepository = repository;
    }

    public void loginOut(LifecycleTransformer<SuccessBean> transformer, Dialog dialog){
        mRepository.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                        if(dialog != null && dialog.isShowing()){
                            dialog.dismiss();
                        }
                        if(mView != null){
                            mView.loginOutSuccess(successBean);
                        }
                }, throwable -> {
                    Log.d("SettingPresenter", throwable.getMessage());
                });
    }

}
