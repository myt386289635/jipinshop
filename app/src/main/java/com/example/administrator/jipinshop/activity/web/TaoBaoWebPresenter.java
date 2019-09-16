package com.example.administrator.jipinshop.activity.web;

import com.example.administrator.jipinshop.activity.WebVieww;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/9/12
 * @Describe
 */
public class TaoBaoWebPresenter {

    private Repository mRepository;
    private TaoBaoWebView mView;

    public void setView(TaoBaoWebView view) {
        mView = view;
    }

    @Inject
    public TaoBaoWebPresenter(Repository repository) {
        mRepository = repository;
    }

    public void taobaoReturnUrl(String code , String state, LifecycleTransformer<SuccessBean> transformer){
        mRepository.taobaoReturnUrl(code, state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess();
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
