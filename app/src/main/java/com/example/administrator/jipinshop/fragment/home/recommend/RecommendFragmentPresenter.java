package com.example.administrator.jipinshop.fragment.home.recommend;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.netwrok.Repository;
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
                    mView.onSuccess(recommendFragmentBean);
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad,
                           AppBarLayout appBarLayout, Boolean[] once){
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                    int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//                    mSwipeToLoad.setRefreshEnabled(topRowVerticalPosition >= 0);
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(verticalOffset == 0){
                //展开
                if(once[0] || mRecyclerView.getVisibility() == View.GONE){//程序打开的第一次运行
                    mSwipeToLoad.setRefreshEnabled(true);
                }else {
                    mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                }
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                mSwipeToLoad.setRefreshEnabled(false);
            }else {
                //过程
                mSwipeToLoad.setRefreshEnabled(false);
            }
        });
    }

}
