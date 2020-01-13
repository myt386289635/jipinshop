package com.example.administrator.jipinshop.activity.mall.exchange;

import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.bean.SucBeanT;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/14
 * @Describe
 */
public class ExchangePresenter {

    private Repository mRepository;
    private ExchangeView mView;

    public void setView(ExchangeView view) {
        mView = view;
    }

    @Inject
    public ExchangePresenter(Repository repository) {
        mRepository = repository;
    }

    public void defaultAddress(LifecycleTransformer<DefaultAddressBean> transformer){
        mRepository.defaultAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(defaultAddressBean -> {
                    if(defaultAddressBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(defaultAddressBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(defaultAddressBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void exchange(String pointGoodsId , String addressId , String total,LifecycleTransformer<SucBeanT<MyOrderBean.DataBean>> transformer){
        mRepository.exchange(pointGoodsId, addressId , total)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if (mView != null){
                            mView.onExchangeSuc(successBean.getData());
                        }
                    }else {
                        if(mView != null){
                            mView.onExchangeFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onExchangeFile(throwable.getMessage());
                    }
                });
    }
}
