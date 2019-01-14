package com.example.administrator.jipinshop.fragment.evaluation.common;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommonEvaluationPresenter {

    private Repository mRepository;
    private CommonEvaluationView mView;

    public void setView(CommonEvaluationView view) {
        mView = view;
    }

    @Inject
    public CommonEvaluationPresenter(Repository repository) {
        mRepository = repository;
    }

    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad, Context context){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 判断RecyclerView是否滑动到底部
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    /**
     * 获取列表数据
     */
    public void getDate(String categoryId ,String page,LifecycleTransformer<EvaluationListBean> transformer){
        mRepository.evaluationList(categoryId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(evaluationListBean -> {
                    if(evaluationListBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(evaluationListBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(evaluationListBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
