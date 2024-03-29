package com.example.administrator.jipinshop.fragment.foval.article;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleView;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class FovalArticlePresenter {

    Repository mRepository;
    private SreachArticleView mView;

    public void setView(SreachArticleView view) {
        mView = view;
    }

    @Inject
    public FovalArticlePresenter(Repository repository) {
        mRepository = repository;
    }


    public void collect(int page,String type ,LifecycleTransformer<QuestionsBean> transformer){
        mRepository.collectQuestions(page + "",type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(fovalBean -> {
                    if (fovalBean.getCode() == 0){
                        if(mView != null){
                            mView.Success(fovalBean);
                        }
                    }else {
                        if(mView != null){
                            mView.Faile(fovalBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.Faile(throwable.getMessage());
                    }
                });
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

}
