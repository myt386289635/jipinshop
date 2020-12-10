package com.example.administrator.jipinshop.activity.web.tuanyou;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/12/10
 * @Describe
 */
public class CZBWebPresenter {

    private Repository mRepository;
    private CZBWebView mView;

    public void setView(CZBWebView view) {
        mView = view;
    }

    @Inject
    public CZBWebPresenter(Repository repository) {
        mRepository = repository;
    }

    //获取专属活动链接
    public void genByAct(String objectId ,String source , LifecycleTransformer<ImageBean> transformer){
        mRepository.genByAct(objectId, source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        if (mView != null){
                            mView.onAction(bean);
                        }
                    }else {
                        if(mView != null){
                            mView.onActionFile();
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onActionFile();
                    }
                });
    }
}
