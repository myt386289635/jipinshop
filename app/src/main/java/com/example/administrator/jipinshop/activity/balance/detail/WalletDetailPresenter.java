package com.example.administrator.jipinshop.activity.balance.detail;

import com.example.administrator.jipinshop.bean.CommssionDetailBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe
 */
public class WalletDetailPresenter {

    private Repository mRepository;
    private WalletDetailView mView;

    public void setView(WalletDetailView view) {
        mView = view;
    }

    @Inject
    public WalletDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void getCommssionDetail2(String orderTime , String type, LifecycleTransformer<CommssionDetailBean> transformer){
        mRepository.getCommssionDetail2(orderTime, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
