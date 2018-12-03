package com.example.administrator.jipinshop.fragment.home.household;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.adapter.HouseholdFragmentGridAdapter;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;
import com.example.administrator.jipinshop.bean.HouseholdFragmentBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HouseholdFragmentPresenter  {

    private Repository mRepository;
    private HouseholdFragmentView mView;

    public void setView(HouseholdFragmentView view) {
        mView = view;
    }

    @Inject
    public HouseholdFragmentPresenter(Repository repository) {
        mRepository = repository;
    }

    public void refreshGirdView(Context context, SwipeToLoadLayout mSwipeToLoadLayout, List<HealthFragmentGridBean> gridViewList,
                                int pos, HouseholdFragmentGridAdapter mAdapter, RecyclerView mRecyclerView){
//        if(mSwipeToLoadLayout.isRefreshing()){
//            Toast.makeText(context, "正在刷新数据，请稍后再试", Toast.LENGTH_SHORT).show();
//            return;
//        }
        for (int i = 0; i < gridViewList.size(); i++) {
            gridViewList.get(i).setTag(false);
        }
        gridViewList.get(pos).setTag(true);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        mSwipeToLoadLayout.setRefreshEnabled(true);
//        mSwipeToLoadLayout.setRefreshing(true);
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

    public void goodRank(String goodsId,LifecycleTransformer<HouseholdFragmentBean> transformer){
        mRepository.goodRank3(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(householdFragmentBean-> {
                    if(householdFragmentBean.getCode() == 200){
                        if(mView != null){
                            mView.onSuccess(householdFragmentBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(householdFragmentBean.getMsg());
                        }
                    }
                }, throwable -> {
                    Log.d("HealthFragmentPresenter", throwable.getMessage());
                });
    }

}
