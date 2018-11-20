package com.example.administrator.jipinshop.activity.info.member;

import com.example.administrator.jipinshop.bean.MemberLevelBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/11/20
 * @Describe
 */
public class MemberLevelPersenter {

    private Repository mRepository;
    private MemberLevelView mView;

    public void setView(MemberLevelView view) {
        mView = view;
    }

    @Inject
    public MemberLevelPersenter(Repository repository) {
        mRepository = repository;
    }

    public void getDate(LifecycleTransformer<MemberLevelBean> transformer){
        mRepository.totalAddPoint()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if(bean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(bean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
