package com.example.administrator.jipinshop.activity.web.hb;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/5/27
 * @Describe
 */
public class HBWebView2Presenter {

    private Repository mRepository;
    private HBWebView2View mView;

    public void setView(HBWebView2View view) {
        mView = view;
    }

    @Inject
    public HBWebView2Presenter(Repository repository) {
        mRepository = repository;
    }

    public void shareCount(String hongbao2Id, String type,String shareType, LifecycleTransformer<SuccessBean> transformer){
        mRepository.shareCount(hongbao2Id, type,shareType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                }, throwable -> {
                });
    }

    public void hbCreatePosterImg(String hongbao2Id, SHARE_MEDIA share_media, LifecycleTransformer<ImageBean> transformer){
        mRepository.hbCreatePosterImg(hongbao2Id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean, share_media);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void getWithdrawInfo(LifecycleTransformer<WithdrawInfoBean> transformer){
        mRepository.getWithdrawInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.withdrawInfo(bean);
                    }
                }, throwable -> {

                });
    }
}
