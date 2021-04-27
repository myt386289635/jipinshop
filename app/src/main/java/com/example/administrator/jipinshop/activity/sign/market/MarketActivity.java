package com.example.administrator.jipinshop.activity.sign.market;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityMarketBinding;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog;
import com.example.administrator.jipinshop.view.dialog.SelectWebPicDialog;

/**
 * @author 莫小婷
 * @create 2020/7/13
 * @Describe 应用市场好评页面 和 公众号页面
 */
public class MarketActivity extends BaseActivity implements View.OnClickListener, SelectWebPicDialog.ChoosePhotoCallback {

    public static final String url = "url";
    public static final String title = "title";

    private ActivityMarketBinding mBinding;
    private Dialog mProgressDialog ;
    //调用相机需要
    private ValueCallback<Uri> mUploadCallbackBelow;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private SelectWebPicDialog mSelectPicDialog;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_market);
        mBinding.setListener(this);
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
                if (url.startsWith("https://tohp")){
                    ShopJumpUtil.jumpMarkets(MarketActivity.this);
                }else if (url.startsWith("http") || url.startsWith("https")) {
                    //解决第三方网页打开页面后会跳转到自定义的schame而页面出错问题
                    view.loadUrl(url);//处理http和https开头的url
                }
                return true;
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

            /**
             * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
                mUploadCallbackBelow = uploadMsg;
                takePhoto();
            }

            /**
             * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg);
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg);
            }

            /**
             * API >= 21(Android 5.0.1)回调此方法
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = filePathCallback;
                takePhoto();
                return true;
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

    //调用相机相册选择器
    private void takePhoto() {
        if(mSelectPicDialog == null){
            mSelectPicDialog = new SelectWebPicDialog();
            mSelectPicDialog.setChoosePhotoCallback(this);
        }
        if(!mSelectPicDialog.isAdded()){
            mSelectPicDialog.show(getSupportFragmentManager(), SelectPicDialog.TAG);
        }
    }

    @Override
    public void getAbsolutePicPath(Uri uri, Intent data) {
        // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
        imageUri = uri;
        if (mUploadCallbackBelow != null) {
            chooseBelow(data);
        } else if (mUploadCallbackAboveL != null) {
            chooseAbove(data);
        } else {
            Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
        }
    }

    //解决Android中webview无法多次弹出相机问题
    @Override
    public void getAbsolutePicFile() {
        if (mUploadCallbackBelow != null) {
            mUploadCallbackBelow.onReceiveValue(null);
            mUploadCallbackBelow = null;
        }
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }

    /**
     * Android API < 21(Android 5.0)版本的回调处理
     * @param data 选取文件或拍照的返回结果
     */
    private void chooseBelow(Intent data) {
        if (data != null) {
            // 这里是针对文件路径处理
            Uri uri = data.getData();
            if (uri != null) {
                mUploadCallbackBelow.onReceiveValue(uri);
            } else {
                mUploadCallbackBelow.onReceiveValue(null);
            }
        } else {
            // 以指定图像存储路径的方式调起相机，成功后返回data为空
            if (imageUri != null){
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }else {
                mUploadCallbackBelow.onReceiveValue(null);
            }
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     * @param data 选取文件或拍照的返回结果
     */
    private void chooseAbove(Intent data) {
        if (data != null) {
            // 这里是针对从文件中选图片的处理
            Uri[] results;
            Uri uriData = data.getData();
            if (uriData != null) {
                results = new Uri[]{uriData};
                mUploadCallbackAboveL.onReceiveValue(results);
            } else {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
        } else {
            if (imageUri != null){
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }else {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
        }
        mUploadCallbackAboveL = null;
    }

}
