package com.example.administrator.jipinshop.activity.wellcome;

import com.example.administrator.jipinshop.bean.StartPageBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
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

    public void getStartupImgs(LifecycleTransformer<StartPageBean> transformer){
        mRepository.getStartupImgs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(startPageBean -> {
                    if (startPageBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(startPageBean);
                        }
                    }
                }, throwable -> {

                });
    }
}
