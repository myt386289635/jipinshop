package com.example.administrator.jipinshop.activity.follow;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class FollowPresenter {

    Repository mRepository;
    private FollowView mView;

    public void setView(FollowView view) {
        mView = view;
    }

    @Inject
    public FollowPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 获取关注列表
     */
    public void concer(int page,LifecycleTransformer<FollowBean> transformer){
        mRepository.concer(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(followBean -> {
                    if(followBean.getCode() == 200){
                        if(mView != null){
                            mView.FollowSuccess(followBean);
                        }
                    }else {
                        if(mView != null){
                            mView.FollowFaileCode(followBean.getMsg());
                        }
                    }

                }, throwable -> {
                    Log.d("FollowPresenter", throwable.getMessage());
                    if(mView != null){
                        mView.FollowFaileCode("网络出错");
                    }
                });
    }

    //解决冲突问题以及滑动卡顿问题
    public void solveScoll(RecyclerView mRecyclerView){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ImageManager.getImageLoader().resume();//为了在滑动时不卡顿
                }else {
                    ImageManager.getImageLoader().pause();//为了在滑动时不卡顿
                }
            }
        });
    }

    /**
     * 取消关注
     */
    public void concerDelete(int pos,String attentionUserId, LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernDelete(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView  != null){
                            mView.ConcerDelSuccess(successBean,pos);
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
    public void concernInsert(String attentionUserId, int pos,LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernInsert(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView  != null){
                            mView.concerInsSuccess(successBean,pos);
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
