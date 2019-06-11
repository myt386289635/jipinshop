package com.example.administrator.jipinshop.activity.sign.invitation;

import com.example.administrator.jipinshop.bean.InvitationBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public class InvitationNewPresenter {

    private Repository mRepository;
    private InvitationNewView mView;

    public void setView(InvitationNewView view) {
        mView = view;
    }

    @Inject
    public InvitationNewPresenter(Repository repository) {
        mRepository = repository;
    }

    public void getQRcodeImgs(LifecycleTransformer<InvitationBean> transformer){
        mRepository.getQRcodeImgs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(invitationBean -> {
                    if (invitationBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(invitationBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(invitationBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

}
