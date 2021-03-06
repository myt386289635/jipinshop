package com.example.administrator.jipinshop.fragment.foval.goods;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.fragment.sreach.goods.SreachGoodsView;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/1/15
 * @Describe
 */
public class FovalGoodsPersenter {

    private Repository mRepository;
    private FovalGoodsView mGoodsView;

    public void setGoodsView(FovalGoodsView goodsView) {
        mGoodsView = goodsView;
    }

    @Inject
    public FovalGoodsPersenter(Repository repository) {
        mRepository = repository;
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

    public void collect(int page,LifecycleTransformer<SucBean<TBSreachResultBean.DataBean>> transformer){
        mRepository.collect(page + "","8")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(fovalBean -> {
                    if (fovalBean.getCode() == 0){
                        if(mGoodsView != null){
                            mGoodsView.Success(fovalBean);
                        }
                    }else {
                        if(mGoodsView != null){
                            mGoodsView.Faile(fovalBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mGoodsView != null){
                        mGoodsView.Faile(throwable.getMessage());
                    }
                });
    }

}
