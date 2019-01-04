package com.example.administrator.jipinshop.fragment.home;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeFragmentPresenter{

    private Repository mRepository;
    private HomeFragmentView mView;

    public void setView(HomeFragmentView view) {
        mView = view;
    }

    @Inject
    public HomeFragmentPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initBadgeView(QBadgeView mQBadgeView, ImageView imageView, Badge.OnDragStateChangedListener onDragStateChangedListener){
        mQBadgeView.bindTarget(imageView)
                .setBadgeTextSize(8,true)
                .setBadgeGravity( Gravity.END | Gravity.TOP)
                .setBadgePadding(2,true)
                .setGravityOffset(11,11,true)
                .setOnDragStateChangedListener(onDragStateChangedListener);
    }

    public void setStatusBarHight(LinearLayout StatusBar ,Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    /**
     * 榜单tab的字段
     */
    public void goodsCategory(LifecycleTransformer<TabBean> ransformer){
       mRepository.goodsCategory()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .compose(ransformer)
               .subscribe(tabBean -> {
                   if (tabBean.getCode() == 0){
                       if(mView != null){
                           mView.Success(tabBean);
                       }
                   }else {
                       if(mView != null){
                           mView.Faile(tabBean.getMsg());
                       }
                   }
               }, throwable -> {
                   if(mView != null){
                       mView.Faile(throwable.getMessage());
                   }
               });
    }

    /**
     * 获取未读消息
     */
    public void unMessage(LifecycleTransformer<UnMessageBean> ransformer){
        mRepository.unMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(unMessageBean -> {
                    if(mView != null){
                        mView.unMessageSuc(unMessageBean);
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.unMessageFaile(throwable.getMessage());
                    }
                });
    }
}
