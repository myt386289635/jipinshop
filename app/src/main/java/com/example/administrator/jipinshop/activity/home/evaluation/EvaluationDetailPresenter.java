package com.example.administrator.jipinshop.activity.home.evaluation;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe
 */
public class EvaluationDetailPresenter {

    private Repository mRepository;

    @Inject
    public EvaluationDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 状态栏
     */
    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }
}
