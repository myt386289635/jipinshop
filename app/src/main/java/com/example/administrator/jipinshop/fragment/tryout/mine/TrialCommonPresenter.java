package com.example.administrator.jipinshop.fragment.tryout.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TrialCommonBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/4/2
 * @Describe
 */
public class TrialCommonPresenter {

    private Repository mRepository;
    private TrialCommonView mView;

    public void setView(TrialCommonView view) {
        mView = view;
    }

    @Inject
    public TrialCommonPresenter(Repository repository) {
        mRepository = repository;
    }

    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //完美解决提交报告后页面移动到第一位时无法刷新
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
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

    public void getDate(String status, String page , LifecycleTransformer<TrialCommonBean> lifecycleTransformer){
        mRepository.myTrialList(status, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleTransformer)
                .subscribe(trialCommonBean -> {
                    if (trialCommonBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(trialCommonBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(trialCommonBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void defaultAddress(LifecycleTransformer<DefaultAddressBean> transformer){
        mRepository.defaultAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(defaultAddressBean -> {
                    if(defaultAddressBean.getCode() == 0){
                        if (mView != null){
                            mView.onAddressSuccess(defaultAddressBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onAddressFile(defaultAddressBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onAddressFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 确认参与
     */
    public void myTrialConfirm(String trialId,LifecycleTransformer<SuccessBean> transformer){
        mRepository.myTrialConfirm(trialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onConfirmSuccess();
                        }
                    }else {
                        if (mView != null){
                            mView.onConfirmFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onConfirmFile(throwable.getMessage());
                    }
                });
    }

}
