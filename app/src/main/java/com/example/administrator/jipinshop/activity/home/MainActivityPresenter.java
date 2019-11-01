package com.example.administrator.jipinshop.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public void initTabLayout(Context context , TabLayout tabLayout,int activityInfo){
        View view1 = LayoutInflater.from(context).inflate(R.layout.tablayout_item1,null);
        tabLayout.getTabAt(0).setCustomView(view1);
        View view3 = LayoutInflater.from(context).inflate(R.layout.tablayout_item3,null);
        tabLayout.getTabAt(1).setCustomView(view3);
        if (activityInfo == 0){// 平常
            View view4 = LayoutInflater.from(context).inflate(R.layout.tablayout_item4,null);
            tabLayout.getTabAt(2).setCustomView(view4);
            View view5 = LayoutInflater.from(context).inflate(R.layout.tablayout_item5,null);
            tabLayout.getTabAt(3).setCustomView(view5);

            View tabView = (View) tabLayout.getTabAt(3).getCustomView().getParent();
            tabView.setOnClickListener(v -> {
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    if (ClickUtil.isFastDoubleClick(800)) {
                    } else {
                        ((Activity)context).startActivityForResult(new Intent(context, LoginActivity.class), 100);
                    }
                }
            });
        }else {
            View viewAticity = LayoutInflater.from(context).inflate(R.layout.tablayout_activityview, null);
            tabLayout.getTabAt(2).setCustomView(viewAticity);
            View view4 = LayoutInflater.from(context).inflate(R.layout.tablayout_item4,null);
            tabLayout.getTabAt(3).setCustomView(view4);
            View view5 = LayoutInflater.from(context).inflate(R.layout.tablayout_item5,null);
            tabLayout.getTabAt(4).setCustomView(view5);

            TextView tab_item1 = view1.findViewById(R.id.tab_item1);
            TextView tab_item3 =view3.findViewById(R.id.tab_item3);
            TextView tab_item4 = view4.findViewById(R.id.tab_item4);
            TextView tab_item5 = view5.findViewById(R.id.tab_item5);
            ImageView tab_activity = viewAticity.findViewById(R.id.tab_activity);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(context)
                    .asGif()
                    .load(R.drawable.tab_activity)
                    .apply(options)
                    .into(tab_activity);
            tabLayout.post(() -> {
                //拿到tabLayout的mTabStrip属性
                LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                int w = (int) context.getResources().getDimension(R.dimen.x70);
                Integer[] textLether = {tab_item1.getWidth() + w,tab_item3.getWidth()+ w,tab_activity.getWidth(),tab_item4.getWidth()+ w,tab_item5.getWidth()+ w};
                int totle = textLether[0] + textLether[1] + textLether[2] + textLether[3] + textLether[4];
                int dp10 = (tabLayout.getWidth() - totle) / textLether.length;
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);
                    tabView.setPadding(0, 0, 0, 0);
                    int width = textLether[i] + dp10;
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                            tabView.getLayoutParams();
                    params.width = width;
                    params.leftMargin = dp10 / 2;
                    params.rightMargin = dp10 / 2;
                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }
            });

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                View tabView = (View) tabLayout.getTabAt(i).getCustomView().getParent();
                int finalI = i;
                tabView.setOnClickListener(v -> {
                    if (finalI == 2){
                        tab_activity.setImageResource(R.mipmap.tab_activity);
                    }else {
                        Glide.with(context)
                                .asGif()
                                .load(R.drawable.tab_activity)
                                .apply(options)
                                .into(tab_activity);
                    }
                    if (finalI == 4){
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                            if (ClickUtil.isFastDoubleClick(800)) {
                            } else {
                                ((Activity)context).startActivityForResult(new Intent(context, LoginActivity.class), 100);
                            }
                        }
                    }
                });
            }
        }
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
