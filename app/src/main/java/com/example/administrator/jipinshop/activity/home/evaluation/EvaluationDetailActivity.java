package com.example.administrator.jipinshop.activity.home.evaluation;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.EvaluationDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ActivityEvaluationDetailBinding;
import com.example.administrator.jipinshop.fragment.foval.FovalFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.example.administrator.jipinshop.view.goodview.GoodView;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe 评测详情
 */
public class EvaluationDetailActivity extends RxAppCompatActivity implements View.OnClickListener, ShareBoardDialog.onShareListener, EvaluationDetailView {

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

    /**
     * 标志：是否点赞过此商品  false:没有
     */
    private boolean isSnap = false;
    /**
     * 标志：是否收藏过此商品 false:没有
     */
    private boolean isCollect = false;

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
        mPresenter.setView(this);
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
        mGoodView = new GoodView(this);

        mPresenter.initDate(getIntent().getStringExtra("id"),this.bindToLifecycle());
        //判断是否收藏过该商品
        mPresenter.isCollect(getIntent().getStringExtra("id"),this.bindToLifecycle());
        //判断是否点赞过该商品
        mPresenter.snapSelect(getIntent().getStringExtra("id"),this.bindToLifecycle());
        //获取评论列表
        mPresenter.comment(getIntent().getStringExtra("id"),this.bindToLifecycle());
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
                if (ClickUtil.isFastDoubleClick(1000)) {
                    Toast.makeText(this, "您点击太快了，请休息会再点", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(isSnap){
                        //点赞过了
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                        mDialog.show();
                        mPresenter.snapDelete(getIntent().getStringExtra("id"),this.bindToLifecycle());
                    }else {
                        //没有点赞
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                        mDialog.show();
                        mPresenter.snapInsert(view,getIntent().getStringExtra("id"),this.bindToLifecycle());
                    }
                }
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
                if (ClickUtil.isFastDoubleClick(1000)) {
                    Toast.makeText(this, "您点击太快了，请休息会再点", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(isCollect){
                        //收藏过了
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                        mDialog.show();
                        mPresenter.collectDelete(getIntent().getStringExtra("id"),this.bindToLifecycle());
                    }else {
                        //没有收藏
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                        mDialog.show();
                        mPresenter.collectInsert(getIntent().getStringExtra("id"),this.bindToLifecycle());
                    }
                }
                break;
            case R.id.bottom_commen:
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position",-1)
                        .putExtra("id",getIntent().getStringExtra("id"))
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

    /**
     * 数据请求成功
     */
    @Override
    public void onSuccess(EvaluationDetailBean bean) {
        ImageManager.displayImage(bean.getGoodsEvalWay().getImgId(),mBinding.detailImageTitle,0,0);
        mBinding.webView.loadDataWithBaseURL(null,
                bean.getGoodsEvalWay().getContent(),
                "text/html", "utf-8", null);
        mBinding.evaTitle.setText(bean.getGoodsEvalWay().getEvalWayName());

    }
    /**
     * 数据请求失败
     */
    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
    }

    /**
     * 查询是否收藏过该文章
     */
    @Override
    public void onSucIsCollect(SnapSelectBean successBean) {
        if(successBean.getCode() == 200){
            isCollect = true;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_sel);
            mBinding.bottomFavorNum.setText(successBean.getCount() + "");
        }else if(successBean.getCode() == 400){
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_nor);
            mBinding.bottomFavorNum.setText(successBean.getCount() + "");
        }else {
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_nor);
            mBinding.bottomFavorNum.setText("0");
            Toast.makeText(this, successBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 查询是否收藏过该文章 失败回调
     */
    @Override
    public void onFileIsCollect(String error) {
        isCollect = false;
        mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_nor);
        mBinding.bottomFavorNum.setText("0");
        Toast.makeText(this, "获取收藏信息失败", Toast.LENGTH_SHORT).show();
    }
    /**
     * 查询是否点赞过该文章
     */
    @Override
    public void onSucIsSnap(SnapSelectBean snapSelectBean) {
        if(snapSelectBean.getCode() == 200){
            isSnap = true;
            mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.detailGoodTv.setText(snapSelectBean.getCount() + "");
        }else if(snapSelectBean.getCode() == 400){
            isSnap = false;
            mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailGoodTv.setText(snapSelectBean.getCount() + "");
        }else {
            isSnap = false;
            mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailGoodTv.setText("0");
            Toast.makeText(this, snapSelectBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 查询是否点赞过该文章 失败回调
     */
    @Override
    public void onFileIsSnap(String error) {
        isSnap = false;
        mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_ACACAC));
        mBinding.detailGoodTv.setText("0");
        Toast.makeText(this, "获取点赞信息失败", Toast.LENGTH_SHORT).show();
    }
    /**
     * 查询该文章的评论数量
     */
    @Override
    public void onSucComment(CommentBean commentBean) {
        mBinding.bottomCommenNum.setText(commentBean.getCount() + "");
    }
    /**
     * 查询该文章的评论数量  失败回调
     */
    @Override
    public void onFileComment(String error) {
        mBinding.bottomCommenNum.setText("0");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加收藏  成功回调
     */
    @Override
    public void onSucCollectInsert(SuccessBean successBean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        isCollect = true;
        mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_sel);
        mBinding.bottomFavorNum.setText(Integer.valueOf(mBinding.bottomFavorNum.getText().toString()) + 1 + "");
    }
    /**
     * 删除收藏  成功回调
     */
    @Override
    public void onSucCollectDelete(SuccessBean successBean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        isCollect = false;
        mBinding.bottomFavor.setImageResource(R.mipmap.nav_favor);
        mBinding.bottomFavorNum.setText(Integer.valueOf(mBinding.bottomFavorNum.getText().toString()) - 1 + "");
        EventBus.getDefault().post(FovalFragment.CollectResher);
    }
    /**
     * 添加、删除收藏  失败回调
     */
    @Override
    public void onFileCollectDelete(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加点赞成功
     */
    @Override
    public void onSucSnapInsert(View view, SuccessBean successBean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mGoodView.setText("+1");
        mGoodView.setTextColor(getResources().getColor(R.color.color_E31436));
        mGoodView.show(view);
        isSnap = true;
        mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_E31436));
        mBinding.detailGoodTv.setText( (Integer.valueOf(mBinding.detailGoodTv.getText().toString()) + 1 )+ "");
    }

    /**
     * 删除点赞成功
     */
    @Override
    public void onSucSnapDelete(SuccessBean successBean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        isSnap = false;
        mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_ACACAC));
        mBinding.detailGoodTv.setText( (Integer.valueOf(mBinding.detailGoodTv.getText().toString()) - 1 )+ "");
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

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }
}
