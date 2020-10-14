package com.example.administrator.jipinshop.activity.web.invite;

import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/10/14
 * @Describe
 */
public class InviteActionWebPresenter {

    private Repository mRepository;
    private InviteActionWebView mView;

    public void setView(InviteActionWebView view) {
        mView = view;
    }

    @Inject
    public InviteActionWebPresenter(Repository repository) {
        mRepository = repository;
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

    public void initShare(SHARE_MEDIA share_media, LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.initShare(share_media,bean);
                    }else {
                        mView.onFlie(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFlie(throwable.getMessage());
                });
    }
}
