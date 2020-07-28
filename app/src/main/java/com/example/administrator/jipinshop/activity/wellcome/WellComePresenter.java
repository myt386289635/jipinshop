package com.example.administrator.jipinshop.activity.wellcome;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/7/28
 * @Describe
 */
public class WellComePresenter {

    private Repository mRepository;

    @Inject
    public WellComePresenter(Repository repository) {
        mRepository = repository;
    }

    public void sendRegTokenToServer(String token ,  LifecycleTransformer<SuccessBean> transformer) {
        mRepository.addToken(2,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {}, throwable -> {});
    }
}
