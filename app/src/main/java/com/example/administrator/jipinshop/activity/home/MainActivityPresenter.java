package com.example.administrator.jipinshop.activity.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {

    private Repository mRepository;
    private MainView mView;

    public void setView(MainView view) {
        mView = view;
    }

    @Inject
    public MainActivityPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initTabLayout(Context context , TabLayout tabLayout){
        View view1 = LayoutInflater.from(context).inflate(R.layout.tablayout_item1,null);
        tabLayout.getTabAt(0).setCustomView(view1);

//        View view2 = LayoutInflater.from(context).inflate(R.layout.tablayout_item2,null);
//        tabLayout.getTabAt(1).setCustomView(view2);

        View view3 = LayoutInflater.from(context).inflate(R.layout.tablayout_item3,null);
        tabLayout.getTabAt(1).setCustomView(view3);

        View view4 = LayoutInflater.from(context).inflate(R.layout.tablayout_item4,null);
        tabLayout.getTabAt(2).setCustomView(view4);

        View view5 = LayoutInflater.from(context).inflate(R.layout.tablayout_item5,null);
        tabLayout.getTabAt(3).setCustomView(view5);
        //水波纹颜色
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
    }

    public void getAppVersion(LifecycleTransformer<AppVersionbean> transformer){
        mRepository.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(appVersionbean -> {
                    if(appVersionbean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(appVersionbean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile();
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile();
                    }
                });
    }

    /**
     * 获取弹窗信息
     */
    public void getPopInfo(LifecycleTransformer<PopInfoBean> transformer){
        mRepository.getPopInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(popInfoBean -> {
                    if (popInfoBean.getCode() == 0){
                        if(mView != null){
                            mView.onDialogSuc(popInfoBean);
                        }
                    }
                }, throwable -> {

                });
    }
}
