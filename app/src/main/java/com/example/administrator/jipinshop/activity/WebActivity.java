package com.example.administrator.jipinshop.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.databinding.ActivityWebBinding;
import com.example.administrator.jipinshop.util.JDUtil;
import com.example.administrator.jipinshop.util.PDDUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/9/3
 * @Describe webView页面
 */
public class WebActivity extends BaseActivity implements View.OnClickListener, WebVieww, ShareBoardDialog.onShareListener {

    @Inject
    WebPresenter mPresenter;
    private ActivityWebBinding mBinding;
    private Dialog mDialog;//加载框
    private Dialog mProgressDialog ;
    private boolean inShare = false;//是否分享  默认为不分享
    //分享面板
    private ShareBoardDialog mShareBoardDialog;
    private String mSource = "";
    private Boolean isGo = false;//是否直接跳转app false否  true是
    private Boolean isFinish = false;//是否需要关闭该页

    public static final String url = "url";
    public static final String title = "title";
    public static final String isShare= "isShare";
    public static final String shareTitle = "shareTitle";
    public static final String shareContent = "shareContent";
    public static final String shareImage = "shareImage";

    public static final String source = "source";
    public static final String go = "go";

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
        inShare = getIntent().getBooleanExtra(isShare,false);
        if (!TextUtils.isEmpty(getIntent().getStringExtra(source))){
            mSource = getIntent().getStringExtra(source);//1京东  2淘宝  4拼多多
        }
        if (getIntent().getBooleanExtra(go,false)){
            isGo = getIntent().getBooleanExtra(go,false);
        }
        mProgressDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "正在加载...");
        mProgressDialog.show();

        mBinding.inClude.titleTv.setText(getIntent().getStringExtra(title));
        mBinding.webView.getSettings().setLoadWithOverviewMode(true);
        mBinding.webView.getSettings().setSupportZoom(true);
        mBinding.webView.getSettings().setBuiltInZoomControls(true);
        mBinding.webView.getSettings().setUseWideViewPort(true);
        mBinding.webView.addJavascriptInterface(new WebViewJavaScriptFunction(), "tk");
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
                        //淘宝授权登陆 没有使用
                        String code = url.replace("https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl?code=","").split("&")[0];
                        String state = url.replace("https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl?code=","").split("&")[1].replace("state=","");
                        mPresenter.taobaoReturnUrl(code, state,WebActivity.this.bindToLifecycle());
                    }else if (url.startsWith("https://login.m.taobao.com")){
                        //淘客web链接里跳转到淘宝app
                        String[] urlValue = url.split("redirectURL=");
                        if (urlValue.length == 2) {
                            try {
                                String decoded_url = URLDecoder.decode(urlValue[1], "UTF-8");
                                if (decoded_url.contains("itemId=")){
                                    String[] str = decoded_url.split("itemId=");
                                    if (str.length == 2) {
                                        String[] value = str[1].split("&");
                                        openTB(value[0]);
                                    }else {
                                        ToastUtil.show("未获得跳转链接");
                                    }
                                }else {
                                    if(url.startsWith("http") || url.startsWith("https")){
                                        //解决第三方网页打开页面后会跳转到自定义的schame而页面出错问题
                                        view.loadUrl(url);//处理http和https开头的url
                                    }
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else if(url.startsWith("http") || url.startsWith("https")){
                            //解决第三方网页打开页面后会跳转到自定义的schame而页面出错问题
                            view.loadUrl(url);//处理http和https开头的url
                        }
                    }else if(url.startsWith("http") || url.startsWith("https")){
                        //解决第三方网页打开页面后会跳转到自定义的schame而页面出错问题
                        view.loadUrl(url);//处理http和https开头的url
                    }else if (url.startsWith("intent://")){
                        //处理intent协议
                        Intent intent;
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            intent.addCategory("android.intent.category.BROWSABLE");
                            intent.setComponent(null);
                            intent.setSelector(null);
                            List<ResolveInfo> resolves = getPackageManager().queryIntentActivities(intent,0);
                            if(resolves.size() >0){
                                startActivityIfNeeded(intent, -1);
                            }
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }else if (!url.startsWith("http")){
                        //处理自定义scheme协议
                        try {
                            // 以下固定写法
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } catch (Exception e) {
                            // 防止没有安装的情况
                            e.printStackTrace();
                        }
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
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            }
        });

        if (inShare){
            mBinding.webShare.setVisibility(View.VISIBLE);
        }else {
            mBinding.webShare.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mSource) && !mSource.equals("0")){
            mPresenter.genByAct(getIntent().getStringExtra(url),mSource,this.bindToLifecycle());
        }else {
            mBinding.webView.loadUrl(getIntent().getStringExtra(url));
        }
    }

    //检查是否是淘客推广链接，否者打不开淘客推广链接
    private boolean checkUrlValid(String aUrl) {
        boolean result = true;
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
            case R.id.web_share:
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog.getInstance("","");
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
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
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
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

    //打开淘宝
    public void openTB(String goodsId){
        TaoBaoUtil.openTB(this, () -> {
            mDialog = (new ProgressDialogView()).createOtherDialog(this,"淘宝",R.mipmap.dialog_tb);
            mDialog.show();
            mPresenter.getGoodsClickUrl("2",goodsId,WebActivity.this.bindToLifecycle());
        });
    }

    @Override
    public void onBuyLinkSuccess(ClickUrlBean bean) {
        TaoBaoUtil.openAliHomeWeb(this,bean.getData().getMobileUrl(),bean.getData().getOtherGoodsId());
    }

    @Override
    public void onAction(ImageBean bean) {
        if (isGo && !TextUtils.isEmpty(mSource) && !mSource.equals("0")){
            isFinish = true;
            if (mSource.equals("1")){
                goJD(bean.getData());
            }else if (mSource.equals("4")){
                goPDD(bean.getData());
            }else {
                goTaobao(bean.getData());
            }
        }else {
            mBinding.webView.loadUrl(bean.getData());
        }
    }

    @Override
    public void onActionFile() {
        if (isGo && !TextUtils.isEmpty(mSource) && !mSource.equals("0")){
            isFinish = true;
            if (mSource.equals("1")){
                goJD(getIntent().getStringExtra(url));
            }else if (mSource.equals("4")){
                goPDD(getIntent().getStringExtra(url));
            }else {
                goTaobao(getIntent().getStringExtra(url));
            }
        }else {
            mBinding.webView.loadUrl(getIntent().getStringExtra(url));
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
        new ShareUtils(this, share_media, mDialog)
                .shareWeb(this, getIntent().getStringExtra(url),
                        getIntent().getStringExtra(shareTitle), getIntent().getStringExtra(shareContent),
                        getIntent().getStringExtra(shareImage), R.mipmap.share_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (isFinish){
            if (isGo && !TextUtils.isEmpty(mSource) && !mSource.equals("0")){
                finish();
            }
        }
    }

    //跳转淘宝
    public void goTaobao(String url) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
        mDialog.show();
        TaoBaoUtil.openAliHomeWeb(WebActivity.this,url,"");
    }

    //跳转拼多多
    public void goPDD(String url){
        mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
        mDialog.show();
        PDDUtil.jumpPDD(WebActivity.this,url,url);
    }

    //跳转京东
    public void goJD(String url){
        mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
        mDialog.show();
        JDUtil.openJD(WebActivity.this, url);
    }

    public class WebViewJavaScriptFunction {

        //跳转淘宝
        @JavascriptInterface
        public void goTaobao(String url) {
            mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
            mDialog.show();
            TaoBaoUtil.openAliHomeWeb(WebActivity.this,url,"");
        }

        //跳转拼多多
        @JavascriptInterface
        public void goPDD(String url){
            mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
            mDialog.show();
            PDDUtil.jumpPDD(WebActivity.this,url,url);
        }

        //跳转京东
        @JavascriptInterface
        public void goJD(String url){
            mDialog = (new ProgressDialogView()).createLoadingDialog(WebActivity.this, "");
            mDialog.show();
            JDUtil.openJD(WebActivity.this, url);
        }
    }
}
