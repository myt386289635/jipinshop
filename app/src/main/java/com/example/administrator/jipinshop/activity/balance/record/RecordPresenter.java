package com.example.administrator.jipinshop.activity.balance.record;

import android.util.Log;

import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe
 */
public class RecordPresenter {

    private Repository mRepository;
    private RecordView mView;

    public void setView(RecordView view) {
        mView = view;
    }

    @Inject
    public RecordPresenter(Repository repository) {
        mRepository = repository;
    }


    public void getRecord(LifecycleTransformer<RecordBean> transformer){
        mRepository.alipay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(recordBean -> {
                    if(recordBean.getCode() == 200){
                        if(mView != null){
                            mView.SuccessRecord(recordBean);
                        }
                    }else {
                        if(mView != null){
                            mView.FaileRecord(recordBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if(mView != null){
                        mView.FaileRecord(throwable.getMessage());
                    }
                    Log.d("RecordPresenter", "throwable:" + throwable.getMessage());
                });
    }
}
