package com.example.administrator.jipinshop.activity.home.classification.encyclopedias;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/7/10
 * @Describe
 */
public class EncyclopediasPresenter {

    private Repository mRepository;
    private EncyclopediasView mView;

    public void setView(EncyclopediasView view) {
        mView = view;
    }

    @Inject
    public EncyclopediasPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(false);//不启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }

    /**
     * 获取数据
     */
    public void getDetail(String id, LifecycleTransformer<FindDetailBean> transformer){
        mRepository.findDetail(id,"5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if(bean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(bean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
