package com.example.administrator.jipinshop.activity.web.dzp;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.PrizeLogBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ActivityWheelWebBinding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/7/1
 * @Describe 大转盘web
 */
public class BigWheelWebActivity extends BaseActivity implements View.OnClickListener, BigWheelWebView, ShareBoardDialog2.onShareListener {

    public static final String url = "url";
    public static final String title = "title";
    public static final String sign = "isSign";

    @Inject
    BigWheelWebPresenter mPresenter;
    private ActivityWheelWebBinding mBinding;
    private Dialog mDialog;//加载框
    private List<PrizeLogBean.DataBean> list;//抽奖记录
    private int isSign = 0;//是否是签到进来的  0否  1是
    private ShareBoardDialog2 mShareBoardDialog;
    private Dialog shareDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_wheel_web);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        isSign = getIntent().getIntExtra(BigWheelWebActivity.sign,0);
        list = new ArrayList<>();
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
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
                if (url.startsWith("https://getjb")){
                    if (isSign == 1){
                        finish();
                    }else {
                        startActivity(new Intent(BigWheelWebActivity.this, SignActivity.class));
                    }
                }else if (url.startsWith("https://cjlist")){
                    //抽奖记录
                    mDialog = (new ProgressDialogView()).createLoadingDialog(BigWheelWebActivity.this, "正在加载...");
                    mDialog.show();
                    mPresenter.prizeLogList(BigWheelWebActivity.this.bindToLifecycle());
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
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
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
                finish();
                break;
            case R.id.web_share:
                //分享
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(0);
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog2");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //防止webView内存溺出
        if (mBinding.webView != null) {
            ViewParent parent = mBinding.webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBinding.webView);
            }
            mBinding.webView.stopLoading();
            mBinding.webView.getSettings().setJavaScriptEnabled(false);
            mBinding.webView.clearHistory();
            mBinding.webView.clearView();
            mBinding.webView.removeAllViews();
            mBinding.webView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSuccess(PrizeLogBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        list.clear();
        list.addAll(bean.getData());
        DialogUtil.onLuckDialog(this,list);
    }

    @Override
    public void onFlie(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (shareDialog != null && shareDialog.isShowing()) {
            shareDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        shareDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        shareDialog.show();
        mPresenter.initShare(share_media,this.bindToLifecycle());
    }

    @Override
    public void initShare(SHARE_MEDIA share_media, ShareInfoBean bean) {
        if (share_media != null){
            new ShareUtils(this,share_media,shareDialog)
                    .shareWeb(this, bean.getData().getLink(),bean.getData().getTitle(),bean.getData().getDesc(),bean.getData().getImgUrl(),R.mipmap.share_logo);
        }else {
            ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("jipinshop",  bean.getData().getLink());
            clip.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
            SPUtils.getInstance().put(CommonDate.CLIP, bean.getData().getLink());
        }
    }

    @Override
    public void onLink() {
        mPresenter.initShare(null,this.bindToLifecycle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shareDialog != null && shareDialog.isShowing()) {
            shareDialog.dismiss();
        }
    }
}
