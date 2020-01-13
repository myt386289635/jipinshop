package com.example.administrator.jipinshop.activity.mall.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/11
 * @Describe
 */
public class MyOrderPresenter {

    private Repository mRepository;
    private MyOrderView mView;

    public void setView(MyOrderView view) {
        mView = view;
    }

    @Inject
    public MyOrderPresenter(Repository repository) {
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

    /**
     * 我的订单列表
     */
    public void orderList(String page ,LifecycleTransformer<MyOrderBean> ransformer){
        mRepository.orderList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(myOrderBean -> {
                    if(myOrderBean.getCode() == 0){
                        if(mView != null){
                            mView.Success(myOrderBean);
                        }
                    }else {
                        if(mView != null){
                            mView.Faile(myOrderBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.Faile(throwable.getMessage());
                    }
                });
    }

    /**
     * 确认收货
     */
    public void orderConfirm(int position ,String orderId ,LifecycleTransformer<SuccessBean> ransformer){
        mRepository.orderConfirm(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.SuccessConfirm(position);
                        }
                    }else {
                        if(mView != null){
                            mView.FaileConfirm(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.FaileConfirm(throwable.getMessage());
                    }
                });
    }
}
