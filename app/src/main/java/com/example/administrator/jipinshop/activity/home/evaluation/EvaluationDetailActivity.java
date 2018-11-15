package com.example.administrator.jipinshop.activity.home.evaluation;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.databinding.ActivityEvaluationDetailBinding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.goodview.GoodView;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe 评测详情
 */
public class EvaluationDetailActivity extends AppCompatActivity implements View.OnClickListener, ShareBoardDialog.onShareListener {

    @Inject
    EvaluationDetailPresenter mPresenter;

    private ActivityEvaluationDetailBinding mBinding;
    private ImmersionBar mImmersionBar;
    private Dialog mDialog;//加载框
    private GoodView mGoodView;  //点赞
    private ShareBoardDialog mShareBoardDialog;

    private Handler.Callback mCallback = msg -> {
        if (msg.what == 1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.webView.getLayoutParams();
            layoutParams.height = (int) (msg.arg1 * getResources().getDisplayMetrics().density);
            mBinding.webView.setLayoutParams(layoutParams);
        }
        return true;
    };
    private Handler handler = new WeakRefHandler(mCallback, Looper.getMainLooper());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_evaluation_detail);
        mBinding.setListener(this);
        DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .build().inject(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();

        mPresenter.initWebView(mBinding.webView);
        mPresenter.setStatusBarHight(mBinding.statusBar,this);

        mBinding.webView.addJavascriptInterface(new WebViewJavaScriptFunction(), "android");
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBinding.webView.loadUrl("javascript:window.android.getBodyHeight(document.body.scrollHeight)");
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
        mBinding.webView.loadUrl("http://192.168.5.136:8080/ueditor/161628719039.html");

        mGoodView = new GoodView(this);
    }

    @Override
    protected void onDestroy() {
        mImmersionBar.destroy();
        UMShareAPI.get(this).release();
        handler.removeCallbacksAndMessages(null);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.detail_good:
                mGoodView.setText("+1");
                mGoodView.setTextColor(getResources().getColor(R.color.color_E31436));
                mGoodView.show(view);
                break;
            case R.id.bottom_share:
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = new ShareBoardDialog();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.bottom_favorLayout:

                break;
            case R.id.bottom_commen:
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position",-1)
//                        .putExtra("id",goodsId)
                );
                break;
            case R.id.bottom_buy:
                Toast.makeText(this, "点击购买", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, "https://www.baidu.com", "测试", "测试而已", "", R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public class WebViewJavaScriptFunction {
        /**
         * 高度
         */
        @JavascriptInterface
        public void getBodyHeight(String number) {
            int webViewHeight = Integer.parseInt(number.split("[.]")[0]);
            Log.e("lgqqqqq======", "webViewHeight" + webViewHeight);

            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = webViewHeight;
            handler.sendMessage(msg);

        }
    }
}