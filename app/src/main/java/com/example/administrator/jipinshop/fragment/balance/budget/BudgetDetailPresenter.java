package com.example.administrator.jipinshop.fragment.balance.budget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe
 */
public class BudgetDetailPresenter {

    private Repository mRepository;

    @Inject
    public BudgetDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad) {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

}
