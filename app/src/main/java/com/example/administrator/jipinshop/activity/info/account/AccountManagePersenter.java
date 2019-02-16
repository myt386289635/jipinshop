package com.example.administrator.jipinshop.activity.info.account;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/2/15
 * @Describe
 */
public class AccountManagePersenter {

    private Repository mRepository;
    private AccountManageView mView;

    public void setView(AccountManageView view) {
        mView = view;
    }

    @Inject
    public AccountManagePersenter(Repository repository) {
        mRepository = repository;
    }

    public void bindThirdAccount(String accessToken, String openid,String channel,LifecycleTransformer<SuccessBean> transformer){
        mRepository.bindThirdAccount(channel,openid,accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (mView != null){
                        mView.onSuccess(successBean);
                    }
                }, throwable -> {
                    ToastUtil.show("授权登陆失败，请检查网络");
                });
    }

}
