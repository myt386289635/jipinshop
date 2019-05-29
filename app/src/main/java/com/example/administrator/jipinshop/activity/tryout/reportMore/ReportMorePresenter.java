package com.example.administrator.jipinshop.activity.tryout.reportMore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.TryReportBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe
 */
public class ReportMorePresenter {
    private Repository mRepository;
    private ReportMoreView mView;

    public void setView(ReportMoreView view) {
        mView = view;
    }

    @Inject
    public ReportMorePresenter(Repository repository) {
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


    public void reportAllList(String page ,String trialId , LifecycleTransformer<TryReportBean> ransformer){
        mRepository.reportAllList(page, trialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(bean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

}
