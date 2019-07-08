package com.example.administrator.jipinshop.activity.home.newarea;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/28
 * @Describe
 */
public class NewAreaPresenter {

    private Repository mRepository;
    private NewAreaView mView;

    public void setView(NewAreaView view) {
        mView = view;
    }

    @Inject
    public NewAreaPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(Toolbar toolbar , ImageView imageView, Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = (int) (context.getResources().getDimension(R.dimen.y80) + statusBarHeight);
            RelativeLayout.LayoutParams textViewLayoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            textViewLayoutParams.topMargin = statusBarHeight;
        }
    }


    public void setTitleBlack(AppBarLayout appBarLayout, ImageView view){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(verticalOffset == 0){
                //展开
                view.setColorFilter(Color.WHITE);
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                view.setColorFilter(Color.BLACK);
            }else {
                //过程
                float a = Math.abs(verticalOffset);
                float b = a / 1000;
                float max = (float) Math.min(1, b * 2);
                int blendCorlor = ColorUtils.blendARGB(Color.WHITE, Color.BLACK, max);
                view.setColorFilter(blendCorlor);
            }
        });
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad,
                           AppBarLayout appBarLayout, Boolean[] once) {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (once[0] ||mRecyclerView.getVisibility() == View.GONE) {
                mSwipeToLoad.setRefreshEnabled(true);
                if(mRecyclerView.getVisibility() == View.GONE){
                    mSwipeToLoad.setLoadMoreEnabled(false);
                }
            } else {
                if (verticalOffset == 0) {
                    //展开
                    mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                    mSwipeToLoad.setLoadMoreEnabled(false);
                } else if (Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()) {
                    //折叠
                    mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
                    mSwipeToLoad.setRefreshEnabled(false);
                } else {
                    //过程
                    mSwipeToLoad.setRefreshEnabled(false);
                    mSwipeToLoad.setLoadMoreEnabled(false);
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

    public void newList(String categoryId , int page , LifecycleTransformer<EvaluationListBean> transformer){
        mRepository.newList(categoryId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(evaluationListBean -> {
                    if (evaluationListBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(evaluationListBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFlie(evaluationListBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFlie(throwable.getMessage());
                    }
                });
    }

}
