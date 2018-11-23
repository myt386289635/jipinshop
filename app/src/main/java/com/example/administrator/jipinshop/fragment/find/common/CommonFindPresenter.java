package com.example.administrator.jipinshop.fragment.find.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommonFindPresenter {

    private Repository mRepository;
    private CommonFindView mView;

    public void setView(CommonFindView view) {
        mView = view;
    }

    @Inject
    public CommonFindPresenter(Repository repository) {
        mRepository = repository;
    }


    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad, Context context){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeToLoad.setRefreshEnabled(topRowVerticalPosition >= 0);
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(context).resumeRequests();//为了在滑动时不卡顿
                }else {
                    Glide.with(context).pauseRequests();//为了在滑动时不卡顿
                }
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
    public void getList(String categoryId,String page,LifecycleTransformer<FindListBean> transformer){
        mRepository.findLis(categoryId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(findListBean -> {
                    if(findListBean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(findListBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(findListBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
