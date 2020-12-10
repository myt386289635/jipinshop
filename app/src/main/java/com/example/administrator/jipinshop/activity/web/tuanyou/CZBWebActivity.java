package com.example.administrator.jipinshop.activity.web.tuanyou;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.web.tuanyou.js.WebPageNavigationJsObject;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.databinding.ActivityWebBinding;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/12/10
 * @Describe 团油web
 */
public class CZBWebActivity extends BaseActivity implements View.OnClickListener, CZBWebView {

    @Inject
    CZBWebPresenter mPresenter;

    private ActivityWebBinding mBinding;
    private String mSource = "";
    private Dialog mProgressDialog ;

    public static final String url = "url";
    public static final String title = "title";
    public static final String source = "source";

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
        mSource = getIntent().getStringExtra(source);
        mBinding.inClude.titleTv.setText(getIntent().getStringExtra(title));
        initWeb();
        mPresenter.genByAct(getIntent().getStringExtra(url),mSource,this.bindToLifecycle());
    }

    private void initWeb() {
        mProgressDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mProgressDialog.show();
        final WebPageNavigationJsObject webPageNavigationJsObject = new WebPageNavigationJsObject(this);
        mBinding.webView.getSettings().setGeolocationEnabled(true);//启用地理定位必须设置成true
        mBinding.webView.getSettings().setUserAgentString("92660094Android");//合作方代码标识
        mBinding.webView.getSettings().setJavaScriptEnabled(true);
        mBinding.webView.getSettings().setDomStorageEnabled(true);
        mBinding.webView.getSettings().setSupportMultipleWindows(true);
        mBinding.webView.getSettings().setAllowContentAccess(true);
        mBinding.webView.getSettings().setDatabaseEnabled(true);
        mBinding.webView.getSettings().setAppCacheEnabled(true);
        mBinding.webView.getSettings().setAllowFileAccess(true);
        mBinding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        mBinding.webView.getSettings().setSavePassword(false);
        mBinding.webView.getSettings().setSaveFormData(false);
        mBinding.webView.getSettings().setUseWideViewPort(true);
        mBinding.webView.getSettings().setLoadWithOverviewMode(true);
        mBinding.webView.getSettings().setTextZoom(100);
        mBinding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mBinding.webView.getSettings().setSupportZoom(true);
        mBinding.webView.getSettings().setBuiltInZoomControls(true);
        mBinding.webView.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        mBinding.webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        mBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mBinding.webView.getSettings().setDefaultTextEncodingName("utf-8");
        mBinding.webView.clearCache(true);
        mBinding.webView.addJavascriptInterface(webPageNavigationJsObject, "czb");
        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {// 网页加载完成
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
            }
        });
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                Log.e("url=", url);
                if (url.startsWith("weixin://") || isHaveAliPayLink(url)) {//如果微信或者支付宝，跳转到相应的app界面,
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(CZBWebActivity.this, "未安装相应的客户端", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

                // 使用h5默认导航需要添加，如果使用app本地导航，则不需要如下代码
                if (url.startsWith("androidamap://route")) {
                    return true;
                }
                if (url.startsWith("http://ditu.amap.com") || url.startsWith("https://ditu.amap.com") || url.startsWith("https://wap.amap.com")) {
                    return true;
                }

                //设置 Header 头方法
                if (webPageNavigationJsObject.getKey() != null) {
                    Map<String,String> extraHeaders = new HashMap();
                    extraHeaders.put(webPageNavigationJsObject.getKey(), webPageNavigationJsObject.getValue());
                    webView.loadUrl(url, extraHeaders);
                } else {
                    webView.loadUrl(url);
                }
                return true;
            }

        });
    }

    //是否是支付宝的 url
    private boolean isHaveAliPayLink(String checkUrl) {
        return !TextUtils.isEmpty(checkUrl) && (checkUrl.startsWith("alipays:") || checkUrl.startsWith("alipay"));
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
    public void onAction(ImageBean bean) {
        mBinding.webView.loadUrl(bean.getData());
    }

    @Override
    public void onActionFile() {
        mBinding.webView.loadUrl(getIntent().getStringExtra(url));
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    //返回上一级
    public void goBack() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
            if (mBinding.webView.getUrl().startsWith("http://m.amap.com") || mBinding.webView.getUrl().startsWith("http://ditu.amap.com/") ||
                    mBinding.webView.getUrl().startsWith("https://m.amap.com") || mBinding.webView.getUrl().startsWith("https://ditu.amap.com/")) {
                mBinding.webView.goBack();
            }
        } else {
            finish();
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
}
