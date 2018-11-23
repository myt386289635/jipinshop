package com.example.administrator.jipinshop.fragment.home.recommend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecommendFragmentPresenter {

    Repository mRepository;
    private RecommendFragmentView mView;

    public void setView(RecommendFragmentView view) {
        mView = view;
    }

    @Inject
    public RecommendFragmentPresenter(Repository repository) {
        mRepository = repository;
    }


    public void getDate(LifecycleTransformer<RecommendFragmentBean> transformer){
        mRepository.ranklist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(recommendFragmentBean -> {
                    if(recommendFragmentBean.getCode() == 200){
                        SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.RecommendFragmentDATA,new Gson().toJson(recommendFragmentBean));
                    }
                    mView.onSuccess(recommendFragmentBean);
                }, throwable -> mView.onFile(throwable.getMessage()));
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad, Context context){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeToLoad.setRefreshEnabled(topRowVerticalPosition >= 0);
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

}
