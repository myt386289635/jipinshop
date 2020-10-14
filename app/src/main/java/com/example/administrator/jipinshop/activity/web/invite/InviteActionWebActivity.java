package com.example.administrator.jipinshop.activity.web.invite;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.example.administrator.jipinshop.activity.money.withdraw.MoneyWithdrawActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.example.administrator.jipinshop.databinding.ActivityWebBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.share.PlatformUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/10/12
 * @Describe 拉新活动
 */
public class InviteActionWebActivity extends BaseActivity implements View.OnClickListener, InviteActionWebView {

    @Inject
    InviteActionWebPresenter mPresenter;
    @Inject
    AppStatisticalUtil appStatisticalUtil;

    private ActivityWebBinding mBinding;
    public static final String url = "url";
    public static final String title = "title";
    private Dialog mDialog;//加载框
    private Dialog mProgressDialog ;
    //提现需要
    private String currentMoney = "";
    private String alipayNickname = "";
    private String realname = "";


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
                if (url.startsWith("https://gotx")){
                    //跳转到提现
                    startActivityForResult(new Intent(InviteActionWebActivity.this, MoneyWithdrawActivity.class)
                                    .putExtra("money", currentMoney)
                                    .putExtra("name",alipayNickname)
                                    .putExtra("realName" , realname)
                            ,300);
                }else if (url.startsWith("https://invitefriends")){
                    //邀请好友分享
                    mDialog = (new ProgressDialogView()).createLoadingDialog(InviteActionWebActivity.this, "");
                    mDialog.show();
                    mPresenter.initShare(SHARE_MEDIA.WEIXIN,InviteActionWebActivity.this.bindToLifecycle());
                }else if (url.startsWith("https://towechattkl")){
                    //复制淘口令后进入微信
                    String content;
                    try {
                        content = URLDecoder.decode(url.replace("https://towechattkl/?copytxt=",""),"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        content = URLDecoder.decode(url.replace("https://towechattkl/?copytxt=",""));
                    }
                    mDialog = (new ProgressDialogView()).createLoadingDialog(InviteActionWebActivity.this, "");
                    new ShareUtils(InviteActionWebActivity.this,SHARE_MEDIA.WEIXIN , mDialog)
                            .shareText(InviteActionWebActivity.this,content);
                }else if (url.startsWith("https://inviterule")){
                    //活动规则
                    startActivity(new Intent(InviteActionWebActivity.this, WebActivity.class)
                            .putExtra(WebActivity.url, RetrofitModule.JP_H5_URL + "new-free/inviteRule")
                            .putExtra(WebActivity.title,"活动规则")
                    );
                }else if (url.startsWith("https://awardrule")){
                    //奖励说明
                    startActivity(new Intent(InviteActionWebActivity.this, WebActivity.class)
                            .putExtra(WebActivity.url, RetrofitModule.JP_H5_URL + "new-free/awardRule")
                            .putExtra(WebActivity.title,"奖励说明")
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
        mPresenter.getWithdrawInfo(this.bindToLifecycle());
        appStatisticalUtil.addEvent("laxin",this.bindToLifecycle());
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
    public void withdrawInfo(WithdrawInfoBean bean) {
        currentMoney = bean.getData().getCurrentMoney();
        alipayNickname = bean.getData().getAlipayNickname();
        realname = bean.getData().getRealname();
    }

    @Override
    public void initShare(SHARE_MEDIA share_media, ShareInfoBean bean) {
        new ShareUtils(this,share_media, mDialog)
                .shareWeb(this, bean.getData().getLink(),bean.getData().getTitle(),bean.getData().getDesc(),bean.getData().getImgUrl(),R.mipmap.share_logo);
    }

    @Override
    public void onFlie(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
