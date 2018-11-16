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
                        mView.FollowFaileNet(throwable.getMessage());
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
     * 取消关注
     */
    public void concerDelete(TextView textView, int pos, Dialog dialog, String concerId, LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernDelete(concerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(mView  != null){
                        mView.ConcerDelSuccess(successBean,textView,pos);
                    }
                }, throwable -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                });
    }
}
