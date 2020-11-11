package com.example.administrator.jipinshop.fragment.play;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.PlayBean;
import com.example.administrator.jipinshop.databinding.FragmentPalyBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/11/10
 * @Describe
 */
public class PlayPresenter {

    private Repository mRepository;
    private PlayView mView;

    public void setView(PlayView view) {
        mView = view;
    }

    @Inject
    public PlayPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    //解决冲突问题
    public void solveScoll(final SwipeToLoadLayout mSwipeToLoad, FragmentPalyBinding mBinding,
                           AppBarLayout appBarLayout, Boolean[] once){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(once[0]){
                mSwipeToLoad.setRefreshEnabled(true);
            }else {
                if(verticalOffset == 0){
                    //展开
                    mSwipeToLoad.setRefreshEnabled(true);
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

    //数据
    public void boxListAll(LifecycleTransformer<PlayBean> transformer){
        mRepository.boxListAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

}
