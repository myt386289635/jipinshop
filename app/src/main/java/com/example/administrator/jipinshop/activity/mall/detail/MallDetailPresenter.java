package com.example.administrator.jipinshop.activity.mall.detail;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommenBannerAdapter;
import com.example.administrator.jipinshop.bean.MallDetailBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.example.administrator.jipinshop.databinding.ActivityMallDetailBinding;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe
 */
public class MallDetailPresenter {

    private Repository mRepository;
    private MallDetailView mView;

    public void setView(MallDetailView view) {
        mView = view;
    }

    @Inject
    public MallDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initHeight(Context context ,ActivityMallDetailBinding mBinding){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.pagerImage.getLayoutParams();
        layoutParams.height = DistanceHelper.getAndroiodScreenwidthPixels(context);
        mBinding.pagerImage.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mBinding.viewPager.getLayoutParams();
        layoutParams1.height = DistanceHelper.getAndroiodScreenwidthPixels(context);
        mBinding.viewPager.setLayoutParams(layoutParams1);
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void initBanner(List<String> mBannerList , Context context , List<ImageView> point, LinearLayout mDetailPoint, CommenBannerAdapter mBannerAdapter){
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

            mDetailPoint.addView(imageView, layoutParams);
        }
        mBannerAdapter.notifyDataSetChanged();
    }

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }

    public void getDate(String goodsId , LifecycleTransformer<MallDetailBean> transformer){
        mRepository.mallDetail(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(mallDetailBean -> {
                    mView.onSuccess(mallDetailBean);
                }, throwable -> mView.onFile(throwable.getMessage()));
    }
}
