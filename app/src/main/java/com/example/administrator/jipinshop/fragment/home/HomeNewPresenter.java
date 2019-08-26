package com.example.administrator.jipinshop.fragment.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TopCategorysListBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/27
 * @Describe
 */
public class HomeNewPresenter {

    private Repository mRepository;
    private HomeNewView mView;

    public void setView(HomeNewView view) {
        mView = view;
    }

    @Inject
    public HomeNewPresenter(Repository repository) {
        mRepository = repository;
    }


    public void setStatusBarHight(Toolbar toolbar ,TextView textView, Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = (int) (context.getResources().getDimension(R.dimen.y120) + statusBarHeight);
            RelativeLayout.LayoutParams textViewLayoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            textViewLayoutParams.topMargin = statusBarHeight;
        }
    }

    /**
     * 获取榜单一级和二级目录
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

    public void initBanner(List<Fragment> mBannerList , Context context , List<ImageView> point, LinearLayout mDetailPoint){
        if (point.size() != mBannerList.size()){
            point.clear();
            mDetailPoint.removeAllViews();
            for (int i = 0; i < mBannerList.size(); i++) {
                ImageView imageView = new ImageView(context);

                point.add(imageView);

                if (i == 0) {
                    imageView.setImageResource(R.drawable.banner_down2);

                } else {
                    imageView.setImageResource(R.drawable.banner_up2);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
                layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);

                mDetailPoint.addView(imageView, layoutParams);
            }
        }
    }

    /**
     * 榜单推荐分类列表
     */
    public void getTopCategorysList(int page, LifecycleTransformer<TopCategorysListBean> transformer){
        mRepository.getTopCategorysList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(topCategorysListBean -> {
                    if (topCategorysListBean.getCode() == 0){
                        if (mView != null){
                            mView.SuccessList(topCategorysListBean);
                        }
                    }else {
                        if (mView != null){
                            mView.FaileList(topCategorysListBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.FaileList(throwable.getMessage());
                    }
                });
    }
}
