package com.example.administrator.jipinshop.fragment.home.commen;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class HomeCommenPresenter {

    private Repository mRepository;
    private HomeCommenView mView;

    public void setView(HomeCommenView view) {
        mView = view;
    }

    @Inject
    public HomeCommenPresenter(Repository repository) {
        mRepository = repository;
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad,
                           AppBarLayout appBarLayout,Boolean[] once){
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
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(once[0]){
                mSwipeToLoad.setRefreshEnabled(true);
            }else {
                if(verticalOffset == 0){
                    //展开
                    mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);

                }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                    //折叠
                    mSwipeToLoad.setRefreshEnabled(false);
                }else {
                    //过程
                    mSwipeToLoad.setRefreshEnabled(false);
                }
            }
        });
    }

    public void goodRank(String goodsId,LifecycleTransformer<HomeCommenBean> transformer){
        mRepository.goodRank(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(commenBean -> {
                    if(commenBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(commenBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(commenBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
