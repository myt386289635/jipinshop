package com.example.administrator.jipinshop.fragment.member.cheap;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/9/17
 * @Describe
 */
public class CheapPresenter {

    private Repository mRepository;
    private CheapView mView;

    public void setView(CheapView view) {
        mView = view;
    }

    @Inject
    public CheapPresenter(Repository repository) {
        mRepository = repository;
    }

    public void addAllowance(String id ,int position, LifecycleTransformer<SuccessBean> transformer){
        mRepository.addAllowance(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(position);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
