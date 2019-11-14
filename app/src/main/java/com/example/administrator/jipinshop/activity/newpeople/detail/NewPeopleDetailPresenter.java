package com.example.administrator.jipinshop.activity.newpeople.detail;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeNewDetailView;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe
 */
public class NewPeopleDetailPresenter {

    private Repository mRepository;
    private FreeNewDetailView mView;

    public void setView(FreeNewDetailView view) {
        mView = view;
    }

    @Inject
    public NewPeopleDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setTitle(AppBarLayout appBarLayout, LinearLayout statusBar){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(verticalOffset == 0){
                //展开
                if (statusBar.getVisibility() != View.INVISIBLE){
                    statusBar.setVisibility(View.INVISIBLE);
                }
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                if (statusBar.getVisibility() != View.VISIBLE){
                    statusBar.setVisibility(View.VISIBLE);
                }
            }else {
                //过程
                if (statusBar.getVisibility() != View.INVISIBLE){
                    statusBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void init(Context context , ViewPager relativeLayout, LinearLayout statusBar, LinearLayout statusBar1, RelativeLayout titleContainer){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
            titleContainer.setMinimumHeight(statusBarHeight);
            ViewGroup.LayoutParams layoutParams1 = statusBar1.getLayoutParams();
            layoutParams1.height = statusBarHeight;
        }

        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = DistanceHelper.getAndroiodScreenwidthPixels(context);
    }

    public void getDate(String freeId, LifecycleTransformer<FreeDetailBean> transformer){
        mRepository.freeDetail(freeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(freeDetailBean2 -> {
                    if (freeDetailBean2.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(freeDetailBean2);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(freeDetailBean2.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void initBanner(List<String> mBannerList , Context context , List<ImageView> point, NoPageBannerAdapter mBannerAdapter){
        for (int i = 0; i < mBannerList.size(); i++) {
            ImageView imageView = new ImageView(context);
            point.add(imageView);
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down);
            } else {
                imageView.setImageResource(R.drawable.banner_up);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);
//            mDetailPoint.addView(imageView, layoutParams);
        }
        mBannerAdapter.notifyDataSetChanged();
    }

    public void freeAppley(String freeId, LifecycleTransformer<ImageBean> transformer){
        mRepository.freeApply2(freeId,"0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        mView.onApply(successBean);
                    }else {
                        mView.onFile(successBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
