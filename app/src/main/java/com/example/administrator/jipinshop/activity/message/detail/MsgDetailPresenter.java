package com.example.administrator.jipinshop.activity.message.detail;

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
public class MsgDetailPresenter {

    private Repository mRepository;
    private MsgDetailView mView;

    public void setView(MsgDetailView view) {
        mView = view;
    }

    @Inject
    public MsgDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void readMsg(String id,LifecycleTransformer<SuccessBean> ransformer){
        mRepository.readMsg(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
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
