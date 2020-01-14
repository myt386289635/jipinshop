package com.example.administrator.jipinshop.activity;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
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

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWebBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/9/3
 * @Describe webView页面
 */
public class WebActivity extends BaseActivity implements View.OnClickListener, WebVieww {

    @Inject
    WebPresenter mPresenter;
    private ActivityWebBinding mBinding;
    private Dialog mDialog;//加载框

    public static final String url = "url";
    public static final String title = "title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "正在加载...");
        mDialog.show();

        mBinding.inClude.titleTv.setText(getIntent().getStringExtra(title));
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
                if (!checkUrlValid(url)) {
                    super.shouldOverrideUrlLoading(view,url);
                    return false;
                } else {
                    if (url.startsWith("https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl")){
                        String code = url.replace("https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl?code=","").split("&")[0];
                        String state = url.replace("https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl?code=","").split("&")[1].replace("state=","");
                        mPresenter.taobaoReturnUrl(code, state,WebActivity.this.bindToLifecycle());
                    }else if(url.startsWith("http") || url.startsWith("https")){
                        //解决第三方网页打开页面后会跳转到自定义的schame而页面出错问题
                        view.loadUrl(url);//处理http和https开头的url
                    }
                    return true;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        //判断页面加载过程
        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {// 网页加载完成
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
            }
        });
        mBinding.webView.loadUrl(getIntent().getStringExtra(url));
    }

    //检查是否是淘客推广链接，否者打不开淘客推广链接
    private boolean checkUrlValid(String aUrl) {
        boolean result = true;
        if (aUrl == null || aUrl.equals("") || !aUrl.contains("http")) {
            return false;
        }
        //淘宝推广链接
        if (aUrl.startsWith("https://s.click") || aUrl.startsWith("http://s.click")) {
            result = false;
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
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

    @Override
    public void onSuccess() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, "--");//临时数据
        ToastUtil.show("授权成功");
        finish();
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
