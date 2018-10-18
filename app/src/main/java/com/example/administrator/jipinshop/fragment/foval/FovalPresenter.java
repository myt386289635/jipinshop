package com.example.administrator.jipinshop.fragment.foval;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class FovalPresenter {

    Repository mRepository;
    private FovalView mView;

    public void setView(FovalView view) {
        mView = view;
    }

    @Inject
    public FovalPresenter(Repository repository) {
        mRepository = repository;
    }


    public void collect(int page,LifecycleTransformer<FovalBean> transformer){
        mRepository.collect(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(fovalBean -> {
                    if (fovalBean.getCode() == 200){
                        if(mView != null){
                            mView.FovalSuccess(fovalBean);
                        }
                    }else {
                        if(mView != null){
                            mView.FovalFaileNet(fovalBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.FovalFaileNet(throwable.getMessage());
                    }
                });
    }


    //解决冲突问题以及滑动卡顿问题
    public void solveScoll(RecyclerView mRecyclerView){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

}
