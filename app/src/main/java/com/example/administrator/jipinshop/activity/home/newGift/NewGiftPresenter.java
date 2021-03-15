package com.example.administrator.jipinshop.activity.home.newGift;

import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2021/3/15
 * @Describe
 */
public class NewGiftPresenter {

    private Repository mRepository;
    private NewGiftView mView;

    public void setView(NewGiftView view) {
        mView = view;
    }

    @Inject
    public NewGiftPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initShare(LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.initShare(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
