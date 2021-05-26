package com.example.administrator.jipinshop.activity.web.exchange;

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

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.eventbus.HomeRefresh;
import com.example.administrator.jipinshop.databinding.ActivityWheelWebBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/5/26
 * @Describe 邀请兑换码活动
 */
public class ExChangeWebActivity extends BaseActivity implements View.OnClickListener, ExChangeWebView {

    @Inject
    ExChangeWebPresenter mPresenter;

    private ActivityWheelWebBinding mBinding;
    public static final String url = "url";
    public static final String share = "share";
    public static final String title = "title";
    private Dialog mProgressDialog ;
    private Dialog mDialog;

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
        if (getIntent().getBooleanExtra(share,false)){
            mBinding.webShare.setVisibility(View.VISIBLE);
            mBinding.webShare.setText("兑换");
            mBinding.webShare.setTextColor(getResources().getColor(R.color.color_202020));
        }else {
            mBinding.webShare.setVisibility(View.GONE);
        }
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
                if (url.startsWith("https://toinvitefriends")){
                    //跳转到立即邀请
                    mDialog = (new ProgressDialogView()).createLoadingDialog(ExChangeWebActivity.this, "");
                    mDialog.show();
                    mPresenter.initShare(ExChangeWebActivity.this.bindToLifecycle());
                }else if (url.startsWith("https://todhlist")){
                    //跳转到立即兑换
                    startActivity(new Intent(ExChangeWebActivity.this, ExChangeWebActivity.class)
                            .putExtra(ExChangeWebActivity.url, RetrofitModule.JP_H5_URL + "new-free/conversionList?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                            .putExtra(ExChangeWebActivity.title, "兑换")
                            .putExtra(ExChangeWebActivity.share, false)
                    );
                }else if (url.startsWith("https://dhsuccess")){
                    //兑换成功
                    EventBus.getDefault().post(new HomeRefresh(HomeRefresh.tag)); //用来刷新首页的
                }else if (url.startsWith("http") || url.startsWith("https")) {
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
                finish();
                break;
            case R.id.web_share:
                startActivity(new Intent(this, ExChangeWebActivity.class)
                        .putExtra(ExChangeWebActivity.url, RetrofitModule.JP_H5_URL + "new-free/conversionList?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                        .putExtra(ExChangeWebActivity.title, "兑换")
                        .putExtra(ExChangeWebActivity.share, false)
                );
                break;
        }
    }

    @Override
    public void initShare(ShareInfoBean bean) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN, mDialog)
                .shareWeb(this, bean.getData().getLink(),bean.getData().getTitle(),bean.getData().getDesc(),
                        bean.getData().getImgUrl(),R.mipmap.share_logo);
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
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
}
