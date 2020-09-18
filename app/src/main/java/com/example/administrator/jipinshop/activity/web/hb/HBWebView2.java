package com.example.administrator.jipinshop.activity.web.hb;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.money.withdraw.MoneyWithdrawActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.example.administrator.jipinshop.databinding.ActivityWebBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/5/27
 * @Describe
 */
public class HBWebView2 extends BaseActivity implements View.OnClickListener,HBWebView2View {

    @Inject
    HBWebView2Presenter mPresenter;
    private ActivityWebBinding mBinding;
    public static final String url = "url";
    private Dialog mDialog;//加载框
    private Dialog mProgressDialog ;
    public static final String title = "title";
    private String hbId = "";//红包id
    private  String type = "";
    private String currentMoney = "";
    private String alipayNickname = "";
    private String realname = "";
    private String shareType = "";

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
        mBinding.inClude.titleTv.setText(getIntent().getStringExtra(title));
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
                if (url.startsWith("https://txhb")){
                    //跳转到提现
                    startActivityForResult(new Intent(HBWebView2.this, MoneyWithdrawActivity.class)
                            .putExtra("money", currentMoney)
                            .putExtra("name",alipayNickname)
                            .putExtra("realName" , realname)
                            ,300);
                }else if (url.startsWith("https://sharehb")){
                    //分享逻辑
                    String[] parameter = url.split("\\?");
                    String[] parames = parameter[1].split("&");
                    shareType = parames[0].replace("shareType=","");
                    hbId = parames[1].replace("hbId=","");
                    if (shareType.equals("6")) {//到提现
                        startActivityForResult(new Intent(HBWebView2.this, MoneyWithdrawActivity.class)
                                        .putExtra("money", currentMoney)
                                        .putExtra("name", alipayNickname)
                                        .putExtra("realName", realname)
                                , 300);
                    } else {
                        //微信分享
                        type = "1";
                        mDialog = (new ProgressDialogView()).createLoadingDialog(HBWebView2.this, "");
                        if (mDialog != null && !mDialog.isShowing()) {
                            mDialog.show();
                        }
                        mPresenter.shareCount(hbId,type,shareType,HBWebView2.this.bindToLifecycle());
                        mPresenter.hbCreatePosterImg(hbId,SHARE_MEDIA.WEIXIN,HBWebView2.this.bindToLifecycle());
                    }
                }else if (url.startsWith("https://openhb")){
                    //打开红包首页
                    startActivity(new Intent(HBWebView2.this, HBWebView2.class)
                            .putExtra(HBWebView2.url, RetrofitModule.JP_H5_URL + "new-free/getRedPacket?isfirst=true&token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                            .putExtra(HBWebView2.title, "天天领现金")
                    );
                }else if (url.startsWith("https://sharewxf")){
                    //shareType=6时跳转到提现页面，否则直接打开微信好友
                    String[] parameter = url.split("\\?");
                    String[] parames = parameter[1].split("&");
                    shareType = parames[0].replace("shareType=","");
                    hbId = parames[1].replace("hbId=","");
                    if (shareType.equals("6")) {//到提现
                        startActivityForResult(new Intent(HBWebView2.this, MoneyWithdrawActivity.class)
                                        .putExtra("money", currentMoney)
                                        .putExtra("name", alipayNickname)
                                        .putExtra("realName", realname)
                                , 300);
                    } else {
                        type = "1";
                        mDialog = (new ProgressDialogView()).createLoadingDialog(HBWebView2.this, "");
                        if (mDialog != null && !mDialog.isShowing()) {
                            mDialog.show();
                        }
                        mPresenter.shareCount(hbId,type,shareType,HBWebView2.this.bindToLifecycle());
                        mPresenter.hbCreatePosterImg(hbId,SHARE_MEDIA.WEIXIN,HBWebView2.this.bindToLifecycle());
                    }
                }else if (url.startsWith("https://sharewxq")){
                    //shareType=6时跳转到提现页面，否则直接打开微信朋友圈
                    String[] parameter = url.split("\\?");
                    String[] parames = parameter[1].split("&");
                    shareType = parames[0].replace("shareType=","");
                    hbId = parames[1].replace("hbId=","");
                    if (shareType.equals("6")) {//到提现
                        startActivityForResult(new Intent(HBWebView2.this, MoneyWithdrawActivity.class)
                                        .putExtra("money", currentMoney)
                                        .putExtra("name", alipayNickname)
                                        .putExtra("realName", realname)
                                , 300);
                    } else {
                        type = "2";
                        mDialog = (new ProgressDialogView()).createLoadingDialog(HBWebView2.this, "");
                        if (mDialog != null && !mDialog.isShowing()) {
                            mDialog.show();
                        }
                        mPresenter.shareCount(hbId,type,shareType,HBWebView2.this.bindToLifecycle());
                        mPresenter.hbCreatePosterImg(hbId,SHARE_MEDIA.WEIXIN_CIRCLE,HBWebView2.this.bindToLifecycle());
                    }
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
        mPresenter.getWithdrawInfo(this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(ImageBean imageBean,SHARE_MEDIA share_media) {
        if (share_media != null){
            new ShareUtils(this, share_media, mDialog)
                    .shareWeb(this,imageBean.getUrl(),
                            imageBean.getTitle(),imageBean.getDesc(),
                            imageBean.getImg(),R.mipmap.share_logo);
        }else {
            Glide.with(this)
                    .asBitmap()
                    .load(imageBean.getData())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                            FileManager.saveFile(resource, HBWebView2.this);
                            ToastUtil.show("已保存到相册");
                        }
                    });
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void withdrawInfo(WithdrawInfoBean bean) {
        currentMoney = bean.getData().getCurrentMoney();
        alipayNickname = bean.getData().getAlipayNickname();
        realname = bean.getData().getRealname();
    }
}
