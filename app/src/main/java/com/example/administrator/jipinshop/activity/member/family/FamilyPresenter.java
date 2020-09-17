package com.example.administrator.jipinshop.activity.member.family;

import com.example.administrator.jipinshop.bean.FamilyBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public class FamilyPresenter {

    private Repository mRepository;
    private FamilyView mView;

    public void setView(FamilyView view) {
        mView = view;
    }

    @Inject
    public FamilyPresenter(Repository repository) {
        mRepository = repository;
    }

    public void familyList(LifecycleTransformer<FamilyBean> transformer){
        mRepository.familyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        mView.onSuccess(successBean);
                    }else {
                        mView.onFile(successBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    //确认加入
    public void familyConfirm(String id , String userId , int position ,LifecycleTransformer<SuccessBean> transformer){
        mRepository.familyConfirm(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        mView.onConfirm(position);
                    }else {
                        mView.onFile(successBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

}
