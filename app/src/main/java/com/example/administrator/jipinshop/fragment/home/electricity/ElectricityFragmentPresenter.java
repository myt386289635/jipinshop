package com.example.administrator.jipinshop.fragment.home.electricity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.adapter.ElectricityFragmentGridAdapter;
import com.example.administrator.jipinshop.bean.ElectricityFragmentBean;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ElectricityFragmentPresenter {

    private Repository mRepository;
    private ElectricityFragmentView mView;

    public void setView(ElectricityFragmentView view) {
        mView = view;
    }

    @Inject
    public ElectricityFragmentPresenter(Repository repository) {
        mRepository = repository;
    }

    public void refreshGirdView(Context context, SwipeToLoadLayout mSwipeToLoadLayout, List<HealthFragmentGridBean> gridViewList,
                                int pos, ElectricityFragmentGridAdapter mAdapter, RecyclerView mRecyclerView){
        if(mSwipeToLoadLayout.isRefreshing()){
            Toast.makeText(context, "正在刷新数据，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < gridViewList.size(); i++) {
            gridViewList.get(i).setTag(false);
        }
        gridViewList.get(pos).setTag(true);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setRefreshing(true);
    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
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
                    ImageManager.getImageLoader().resume();//为了在滑动时不卡顿
                }else {
                    ImageManager.getImageLoader().pause();//为了在滑动时不卡顿
                }
            }
        });
    }

    public void goodRank(String goodsId,LifecycleTransformer<ElectricityFragmentBean> transformer){
        mRepository.goodRank4(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(electricityFragmentBean -> {
                    if(electricityFragmentBean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(electricityFragmentBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(electricityFragmentBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
