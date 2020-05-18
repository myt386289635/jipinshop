package com.example.administrator.jipinshop.activity.setting.about;

import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/5/15
 * @Describe
 */
public class AboutPresenter {

    private Repository mRepository;
    private AboutView mView;

    public void setView(AboutView view) {
        mView = view;
    }

    @Inject
    public AboutPresenter(Repository repository) {
        mRepository = repository;
    }

    public void getAppVersion(LifecycleTransformer<AppVersionbean> transformer){
        mRepository.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(appVersionbean -> {
                    if(appVersionbean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(appVersionbean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(appVersionbean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
