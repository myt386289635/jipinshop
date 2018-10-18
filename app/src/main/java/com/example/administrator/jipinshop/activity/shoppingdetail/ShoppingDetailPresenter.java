package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.ShoppingBannerAdapter;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.FullScreenLinearLayout;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe
 */
public class ShoppingDetailPresenter {

    Repository mRepository;
    private ShoppingDetailView mShoppingDetailView;

    public void setShoppingDetailView(ShoppingDetailView shoppingDetailView) {
        mShoppingDetailView = shoppingDetailView;
    }

    @Inject
    public ShoppingDetailPresenter(Repository repository) {
        mRepository = repository;
    }


    public void initBanner(List<String> mBannerList , Context context , List<ImageView> point,LinearLayout mDetailPoint, ShoppingBannerAdapter mBannerAdapter){
        for (int i = 0; i < 5; i++) {
            mBannerList.add("http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/03/14/c0391a6c1efab3fe00911b04e8cedca4.jpg");
        }
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


    public void initTitleLayout( Context context ,TextView showTv, View showVi , TextView hintTv1,View hintVi1 , TextView hintTv2,View hintVi2 ){
        showTv.setTextColor(context.getResources().getColor(R.color.color_DE050505));
        showVi.setVisibility(View.VISIBLE);
        hintTv1.setTextColor(context.getResources().getColor(R.color.color_DEACACAC));
        hintVi1.setVisibility(View.INVISIBLE);
        hintTv2.setTextColor(context.getResources().getColor(R.color.color_DEACACAC));
        hintVi2.setVisibility(View.INVISIBLE);
    }

    public void setKeyListener(final FullScreenLinearLayout mDetailContanier , final int[] usableHeightPrevious){
        mDetailContanier.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        possiblyResizeChildOfContent(mDetailContanier,usableHeightPrevious);
                    }
                });
    }

    /****************监听软键盘的情况**********************/
    private void possiblyResizeChildOfContent(FullScreenLinearLayout mDetailContanier ,  int[] usableHeightPrevious) {
        int usableHeightNow = computeUsableHeight(mDetailContanier);
        if (usableHeightNow != usableHeightPrevious[0]) {
            int usableHeightSansKeyboard = mDetailContanier.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 键盘弹出
                mShoppingDetailView.keyShow();
            } else {
                // 键盘收起
                mShoppingDetailView.keyHint();
            }
            mDetailContanier.requestLayout();
            usableHeightPrevious[0] = usableHeightNow;
        }
    }

    private int computeUsableHeight(FullScreenLinearLayout mDetailContanier) {
        Rect r = new Rect();
        mDetailContanier.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }

    public void getDate(LifecycleTransformer<RecommendFragmentBean> transformer){
        mRepository.ranklist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(recommendFragmentBean -> {
                    mShoppingDetailView.onSuccess(recommendFragmentBean);
                }, throwable -> mShoppingDetailView.onFile(throwable.getMessage()));
    }

}
