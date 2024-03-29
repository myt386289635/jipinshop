package com.example.administrator.jipinshop.activity.web.exchange;

import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2021/5/26
 * @Describe
 */
public class ExChangeWebPresenter {

    private Repository mRepository;
    private ExChangeWebView mView;

    public void setView(ExChangeWebView view) {
        mView = view;
    }

    @Inject
    public ExChangeWebPresenter(Repository repository) {
        mRepository = repository;
    }

    //分享五重礼
    public void initShare(LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(11)
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

    //分享兑换卡
    public void initShare(String code, SHARE_MEDIA share_media , LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(12,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onShare(bean, share_media);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
