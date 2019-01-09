package com.example.administrator.jipinshop.fragment.sreach.goods;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
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
public class SreachGoodsPresenter {

    private Repository mRepository;
    private SreachGoodsView mView;

    public void setView(SreachGoodsView view) {
        mView = view;
    }

    @Inject
    public SreachGoodsPresenter(Repository repository) {
        mRepository = repository;
    }

    public void searchGoods(String page ,String goodsName,LifecycleTransformer<SreachResultGoodsBean> transformer){
        mRepository.searchGoods(page,"1",goodsName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(sreachResultBean -> {
                    if (sreachResultBean.getCode() == 0){
                        if(mView != null){
                            mView.Success(sreachResultBean);
                        }
                    }else {
                        if(mView != null){
                            mView.Faile(sreachResultBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.Faile(throwable.getMessage());
                    }
                });
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad, AppBarLayout appBarLayout) {
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
            if(verticalOffset == 0){
                //展开
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                mSwipeToLoad.setLoadMoreEnabled(false);
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
                mSwipeToLoad.setRefreshEnabled(false);
            }else {
                //过程
                mSwipeToLoad.setRefreshEnabled(false);
                mSwipeToLoad.setLoadMoreEnabled(false);
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
