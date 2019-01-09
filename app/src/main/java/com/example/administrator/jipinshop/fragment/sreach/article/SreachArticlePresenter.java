package com.example.administrator.jipinshop.fragment.sreach.article;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class SreachArticlePresenter {

    private Repository mRepository;
    private SreachArticleView mView;

    public void setView(SreachArticleView view) {
        mView = view;
    }

    @Inject
    public SreachArticlePresenter(Repository repository) {
        mRepository = repository;
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

    public void searchGoods(String page,String type, String goodsName, LifecycleTransformer<SreachResultArticlesBean> transformer) {
        mRepository.searchArticles(page, type, goodsName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(sreachResultBean -> {
                    if (sreachResultBean.getCode() == 0) {
                        if (mView != null) {
                            mView.Success(sreachResultBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.Faile(sreachResultBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.Faile(throwable.getMessage());
                    }
                });
    }
}
