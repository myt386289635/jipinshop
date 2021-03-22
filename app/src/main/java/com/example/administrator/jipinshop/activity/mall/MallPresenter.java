package com.example.administrator.jipinshop.activity.mall;

import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe
 */
public class MallPresenter {

    private Repository mRepository;
    private MallView mView;

    public void setView(MallView view) {
        mView = view;
    }

    @Inject
    public MallPresenter(Repository repository) {
        mRepository = repository;
    }

    public void mallList(int page,LifecycleTransformer<MallBean> transformer){
        mRepository.mallList(page+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(mallBean -> {
                    if(mallBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(mallBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(mallBean.getMsg());
                        }
                    }
                }, throwable ->{
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void hotList(LifecycleTransformer<MallBean> transformer){
        mRepository.mallList("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(mallBean -> {
                    if(mallBean.getCode() == 0){
                        mView.onHot(mallBean);
                    }else {
                        mView.onHotFile(mallBean.getMsg());
                    }
                }, throwable ->{
                    mView.onHotFile(throwable.getMessage());
                });
    }
}
