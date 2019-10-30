package com.example.administrator.jipinshop.activity.wellcome;

import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/5/9
 * @Describe
 */
public class WellComePresenter {

    private Repository mRepository;
    private WellComeView mView;

    public void setView(WellComeView view) {
        mView = view;
    }

    @Inject
    public WellComePresenter(Repository repository) {
        mRepository = repository;
    }

    public void open11(LifecycleTransformer<ScoreStatusBean> transformer){
        mRepository.open11()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(startPageBean -> {
                    if (startPageBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(startPageBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile();
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile();
                    }
                });
    }
}
