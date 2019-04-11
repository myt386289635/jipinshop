package com.example.administrator.jipinshop.activity.order.detail;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/4/10
 * @Describe
 */
public class OrderDetailPresenter {

    private Repository mRepository;
    private OrderDetailView mView;

    public void setView(OrderDetailView view) {
        mView = view;
    }

    @Inject
    public OrderDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 确认收货
     */
    public void orderConfirm(String orderId ,LifecycleTransformer<SuccessBean> ransformer){
        mRepository.orderConfirm(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.SuccessConfirm();
                        }
                    }else {
                        if(mView != null){
                            mView.FaileConfirm(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.FaileConfirm(throwable.getMessage());
                    }
                });
    }
}
