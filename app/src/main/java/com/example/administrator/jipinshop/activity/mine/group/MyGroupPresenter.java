package com.example.administrator.jipinshop.activity.mine.group;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.GroupInfoBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/11/25
 * @Describe
 */
public class MyGroupPresenter {

     Repository mRepository;
     private MyGroupView mView;

    public void setView(MyGroupView view) {
        mView = view;
    }

    @Inject
    public MyGroupPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , RelativeLayout relativeLayout, Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;

            LinearLayout.LayoutParams container = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            int top = context.getResources().getDimensionPixelSize(R.dimen.y288) - statusBarHeight;
            container.topMargin = top;
            relativeLayout.setLayoutParams(container);
        }
    }

    /**
     * 猜你喜欢
     */
    public void listSimilerGoods(Map<String,String> map, LifecycleTransformer<SimilerGoodsBean> transformer){
        Map<String,String> parament = new HashMap<>();
        parament.putAll(map);
        mRepository.listSimilerGoods(parament)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.LikeSuccess(bean);
                    }else {
                        mView.onFaile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFaile(throwable.getMessage());
                });
    }

    /**
     * 获取拼团信息
     */
    public void groupInfo(String groupId, LifecycleTransformer<GroupInfoBean> transformer){
        mRepository.groupInfo(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFaile(bean.getMsg());
                    }
                },throwable -> {
                    mView.onFaile(throwable.getMessage());
                });
    }
}
