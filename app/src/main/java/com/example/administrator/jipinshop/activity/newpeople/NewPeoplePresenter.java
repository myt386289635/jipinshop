package com.example.administrator.jipinshop.activity.newpeople;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewPeopleBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe
 */
public class NewPeoplePresenter {

    private Repository mRepository;
    private NewPeopleView mView;

    public void setView(NewPeopleView view) {
        mView = view;
    }

    @Inject
    public NewPeoplePresenter(Repository repository) {
        mRepository = repository;
    }

    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void getData(LifecycleTransformer<NewPeopleBean> transformer){
        mRepository.newIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(v2FreeListBean -> {
                    if (v2FreeListBean.getCode() == 0){
                        mView.onSuccess(v2FreeListBean);
                    }else {
                        mView.onFile(v2FreeListBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void apply(String allowanceGoodsId , LifecycleTransformer<ImageBean> transformer){
        mRepository.allowanceApply(allowanceGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onBuySuccess(bean);
                    }else {
                        mView.onBuyFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onBuyFile(throwable.getMessage());
                });
    }
}
