package com.example.administrator.jipinshop.activity.home.classification;

import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe
 */
public class ClassifyPresenter {

    private Repository mRepository;
    private ClassifyView mView;

    public void setView(ClassifyView view) {
        mView = view;
    }

    @Inject
    public ClassifyPresenter(Repository repository) {
        mRepository = repository;
    }

    public void getTopCategoryDetail(String categoryId , LifecycleTransformer<TopCategoryDetailBean> transformer){
        mRepository.getTopCategoryDetail(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(topCategoryDetailBean -> {
                    if (topCategoryDetailBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(topCategoryDetailBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(topCategoryDetailBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void goodsListByOrderbyCategoryId(String orderbyCategoryId , LifecycleTransformer<SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean>> transformer){
        mRepository.goodsListByOrderbyCategoryId(orderbyCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(relatedGoodsListBeanSucBean -> {
                    if (relatedGoodsListBeanSucBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccessSed(relatedGoodsListBeanSucBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFlieSed(relatedGoodsListBeanSucBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFlieSed(throwable.getMessage());
                    }
                });
    }
}
