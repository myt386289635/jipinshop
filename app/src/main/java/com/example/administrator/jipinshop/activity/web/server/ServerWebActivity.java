package com.example.administrator.jipinshop.activity.web.server;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWebBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author 莫小婷
 * @create 2021/4/6
 * @Describe 客服与帮助
 */
public class ServerWebActivity extends BaseActivity implements View.OnClickListener {

    public static final String url = "url";

    private ActivityWebBinding mBinding;
    private Dialog mProgressDialog ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("人工客服");
        mProgressDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mProgressDialog.show();

        mBinding.webView.getSettings().setLoadWithOverviewMode(true);
        mBinding.webView.getSettings().setSupportZoom(true);
        mBinding.webView.getSettings().setBuiltInZoomControls(true);
        mBinding.webView.getSettings().setUseWideViewPort(true);
        mBinding.webView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mBinding.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mBinding.webView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
        // 设置允许加载混合内容 https与http混合使用的网址
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://tofeedback")){
                    //跳转到反馈中心
                    startActivity(new Intent(ServerWebActivity.this, OpinionActivity.class));
                }else if (url.startsWith("https://toqlist")){
                    //跳转到其他web里
                    String str = url.replace("https://toqlist/?","");
                    String[] newStr = str.split("&");
                    String id = newStr[0].replace("id=","");
                    String title = newStr[1].replace("title=","");
                    try {
                        startActivity(new Intent(ServerWebActivity.this, WebActivity.class)
                                .putExtra(WebActivity.url , RetrofitModule.JP_H5_URL + "new-free/questionList?id=" + id)
                                .putExtra(WebActivity.title , URLDecoder.decode(title, "UTF-8"))
                        );
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        startActivity(new Intent(ServerWebActivity.this, WebActivity.class)
                                .putExtra(WebActivity.url , RetrofitModule.JP_H5_URL + "new-free/questionList?id=" + id)
                                .putExtra(WebActivity.title , "自助服务")
                        );
                    }

                }else if (url.startsWith("https://toqinfo")){
                    //跳转到其他web里
                    String qInfo = url.replace("https://toqinfo/?qInfo=","");
                    startActivity(new Intent(ServerWebActivity.this, WebActivity.class)
                            .putExtra(WebActivity.url , RetrofitModule.JP_H5_URL + "new-free/questionInfo?qInfo=" + qInfo)
                            .putExtra(WebActivity.title , "常见问题")
                    );
                } else if (url.startsWith("http") || url.startsWith("https")) {
                    //解决第三方网页打开页面后会跳转到自定义的schame而页面出错问题
                    view.loadUrl(url);//处理http和https开头的url
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }
        });
        //判断页面加载过程
        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {// 网页加载完成
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            }
        });
        mBinding.webView.loadUrl(getIntent().getStringExtra(url));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                goBack();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //防止webView内存溺出
        if (mBinding.webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mBinding.webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBinding.webView);
            }
            mBinding.webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mBinding.webView.getSettings().setJavaScriptEnabled(false);
            mBinding.webView.clearHistory();
            mBinding.webView.clearView();
            mBinding.webView.removeAllViews();
            mBinding.webView.destroy();
        }
        super.onDestroy();
    }

    //返回上一级
    public void goBack() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
