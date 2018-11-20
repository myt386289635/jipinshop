package com.example.administrator.jipinshop.activity.integral.detail;

import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class IntegralDetailPresenter {

    private Repository mRepository;
    private IntegralDetailView mView;

    public void setView(IntegralDetailView view) {
        mView = view;
    }

    @Inject
    public IntegralDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void getDate(LifecycleTransformer<PointDetailBean> transformer){
        mRepository.pointDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(pointDetailBean -> {
                    if(pointDetailBean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(pointDetailBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(pointDetailBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });

    }
}
