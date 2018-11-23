package com.example.administrator.jipinshop.activity.follow.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.commonsdk.statistics.common.MLog;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class UserPresenter {

    private Repository mRepository;
    private UserView mView;

    public void setView(UserView view) {
        mView = view;
    }

    @Inject
    public UserPresenter(Repository repository) {
        mRepository = repository;
    }

    //解决冲突问题以及滑动卡顿问题
    public void solveScoll(RecyclerView mRecyclerView, Context context){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

    public void getList(String attentionUserId,String page,LifecycleTransformer<UserPageBean> transformer){
        mRepository.userPage(attentionUserId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(userPageBean -> {
                    if(userPageBean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(userPageBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(userPageBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 取消关注
     */
    public void concerDelete(String attentionUserId, LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernDelete(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView  != null){
                            mView.ConcerDelSuccess(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.ConcerDelFaile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.ConcerDelFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加关注
     */
    public void concernInsert(String attentionUserId,LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernInsert(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView  != null){
                            mView.concerInsSuccess(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.ConcerDelFaile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.ConcerDelFaile(throwable.getMessage());
                    }
                });
    }
}
