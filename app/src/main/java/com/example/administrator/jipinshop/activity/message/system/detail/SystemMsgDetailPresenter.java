package com.example.administrator.jipinshop.activity.message.system.detail;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe
 */
public class SystemMsgDetailPresenter {

    private Repository mRepository;
    private SystemMsgDetailView mView;

    public void setView(SystemMsgDetailView view) {
        mView = view;
    }

    @Inject
    public SystemMsgDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void readMsg(String messageId,LifecycleTransformer<SuccessBean> ransformer){
        mRepository.readMsg(messageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }

}
