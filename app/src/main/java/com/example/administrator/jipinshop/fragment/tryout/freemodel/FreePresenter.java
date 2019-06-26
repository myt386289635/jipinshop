package com.example.administrator.jipinshop.fragment.tryout.freemodel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.FreeBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/19
 * @Describe
 */
public class FreePresenter {

    private Repository mRepository;
    private FreeView mView;

    public void setView(FreeView view) {
        mView = view;
    }

    @Inject
    public FreePresenter(Repository repository) {
        mRepository = repository;
    }


    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));

                //解决item占满全屏时无法下拉刷新
                View firstChild = null;
                if (recyclerView.getChildCount() > 0) {
                    firstChild = recyclerView.getChildAt(0);
                }
                int firstChildPosition = firstChild == null ? 0 : recyclerView.getChildLayoutPosition(firstChild);
                if ( firstChild != null){
                    mSwipeToLoad.setRefreshEnabled(firstChildPosition == 0 && firstChild.getTop()>=0);//如果firstChild处于列表的第一个位置，且top>=0,则下拉刷新控件可用
                }else {
                    //第一个item没有占满全屏时可用
                    mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                }

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

    public void freeList(int page, LifecycleTransformer<FreeBean> transformer){
        mRepository.freeList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(freeBean -> {
                    if (freeBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(freeBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(freeBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }


}
