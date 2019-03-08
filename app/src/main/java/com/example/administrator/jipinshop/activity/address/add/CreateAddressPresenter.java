package com.example.administrator.jipinshop.activity.address.add;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe
 */
public class CreateAddressPresenter {

    private Repository mRepository;
    private CreateAddressView mView;

    public void setView(CreateAddressView view) {
        mView = view;
    }

    @Inject
    public CreateAddressPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 新增地址
     */
    public void addressAdd(String username, String mobile,String address,String area,LifecycleTransformer<SuccessBean> transformer){
        mRepository.addressAdd(username,mobile,address,area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess();
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 修改收货地址
     */
    public void addressUpdate(String addressId , String username,
                              String mobile, String address,String area,LifecycleTransformer<SuccessBean> transformer){
        mRepository.addressUpdate(addressId,username,mobile,address,area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess();
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
