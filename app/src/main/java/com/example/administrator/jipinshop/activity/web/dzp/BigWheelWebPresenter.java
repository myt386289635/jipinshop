package com.example.administrator.jipinshop.activity.web.dzp;

import com.example.administrator.jipinshop.bean.PrizeLogBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/7/1
 * @Describe
 */
public class BigWheelWebPresenter {

    private Repository mRepository;
    private BigWheelWebView mView;

    @Inject
    public BigWheelWebPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setView(BigWheelWebView view) {
        mView = view;
    }

    public void prizeLogList(LifecycleTransformer<PrizeLogBean> transformer){
        mRepository.prizeLogList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFlie(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFlie(throwable.getMessage());
                });
    }

}
