package com.example.administrator.jipinshop.activity.home.find;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.adapter.FindBannerAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.FindBus;
import com.example.administrator.jipinshop.databinding.ActivityFindDetailBinding;
import com.example.administrator.jipinshop.fragment.foval.FovalFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.view.goodview.GoodView;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/30
 * @Describe 发现的详情
 */
public class FindDetailActivity extends RxAppCompatActivity implements View.OnClickListener, ShareBoardDialog.onShareListener, FindDetailView {

    @Inject
    FindDetailPresenter mPresenter;
    private ActivityFindDetailBinding mBinding;

    private Dialog mDialog;//加载框
    private ImmersionBar mImmersionBar;
    private Boolean stopThread = true;
    private FindBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    //点赞
    private GoodView mGoodView;
    private ShareBoardDialog mShareBoardDialog;
    /**
     * 标志：是否点赞过此商品  false:没有
     */
    private boolean isSnap = false;
    /**
     * 标志：是否收藏过此商品 false:没有
     */
    private boolean isCollect = false;

    private Handler.Callback mCallback = msg -> {
        if (msg.what == 1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.webView.getLayoutParams();
            layoutParams.height = (int) (msg.arg1 * getResources().getDisplayMetrics().density);
            mBinding.webView.setLayoutParams(layoutParams);
        }else if(msg.what == 100){
            //banner
            if (mBinding.viewPager != null) {
                mBinding.viewPager.setCurrentItem(mBinding.viewPager.getCurrentItem() + 1);
            }
        }
        return true;
    };
    private Handler handler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    /**
     * 分享的东西
     */
    private String shareImage = "";
    private String shareTitle = "";
    private String shareSubTitle = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_find_detail);
        mBinding.setListener(this);
        DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .build().inject(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(FindDetailActivity.this, "正在加载...");
        mDialog.show();

        mPresenter.setView(this);
        mPresenter.initWebView(mBinding.webView);
        mPresenter.setStatusBarHight(mBinding.statusBar,mBinding.titleBack,this);
        mBinding.webView.addJavascriptInterface(new WebViewJavaScriptFunction(), "android");
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("moxiaoting", "" + url);
                if(url.contains("http://qualityshop/?")){
                    mDialog = (new ProgressDialogView()).createLoadingDialog(FindDetailActivity.this, "");
                    if(mDialog != null && !mDialog.isShowing()){
                        mDialog.show();
                    }
                    String link = url.replace("http://qualityshop/?buyId=","");
                    openAliHomeWeb(link);
                    return true;
                }else {
                    view.loadUrl(url);
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBinding.webView.loadUrl("javascript:window.android.getBodyHeight(document.body.scrollHeight)");
            }
        });
        //判断页面加载过程
//        mBinding.webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {// 网页加载完成
//                    if (mDialog.isShowing()) {
//                        mDialog.dismiss();
//                    }
//                }
//            }
//        });

        mBannerAdapter = new FindBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager( mBinding.viewPager);
        mBinding.viewPager.setAdapter(mBannerAdapter);
        mBinding.viewPager.setCurrentItem(mBannerList.size() * 10);

        mGoodView = new GoodView(this);

        mPresenter.getDetail(getIntent().getStringExtra("id"),this.bindToLifecycle());
        //判断是否收藏过该商品
        mPresenter.isCollect(getIntent().getStringExtra("id"),this.bindToLifecycle());
        //判断是否点赞过该商品
        mPresenter.snapSelect(getIntent().getStringExtra("id"),this.bindToLifecycle());
//        //获取评论列表
//        mPresenter.comment(getIntent().getStringExtra("id"),this.bindToLifecycle());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AlibcTradeSDK.destory();
        stopThread = false;
        mImmersionBar.destroy();
        UMShareAPI.get(this).release();
        handler.removeCallbacksAndMessages(null);
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
    public void onClick(View view) {
        switch (view.getId()) {
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
                        .putExtra("type","3")
                        .putExtra("id",getIntent().getStringExtra("id"))
                );
                break;
        }
    }

    /**
     * 分享
     * @param share_media
     */
    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, RetrofitModule.UP_BASE_URL + "share/find-info.html?findgoodsId=" + getIntent().getStringExtra("id"), shareTitle, shareSubTitle, shareImage, R.mipmap.share_logo);
    }

    /**
     * 获取详情页数据
     */
    @Override
    public void onSuccess(FindDetailBean bean) {
        shareTitle = bean.getGoodsFindGoods().getTitle();
        shareSubTitle = bean.getGoodsFindGoods().getSmallTitle();
        shareImage = getIntent().getStringExtra("image");
        //轮播图设置值
        if(bean.getGoodsFindGoods().getImgList() != null && bean.getGoodsFindGoods().getImgList().size() != 0){
            if(bean.getGoodsFindGoods().getImgList().size() == 1){
                mBinding.viewPager.setVisibility(View.GONE);
                mBinding.detailPoint.setVisibility(View.GONE);
                mBinding.pagerImage.setVisibility(View.VISIBLE);
                GlideApp.loderImage(this,bean.getGoodsFindGoods().getImgList().get(0).getImgPath(),mBinding.pagerImage,0,0);
            }else {
                mBinding.viewPager.setVisibility(View.VISIBLE);
                mBinding.detailPoint.setVisibility(View.VISIBLE);
                mBinding.pagerImage.setVisibility(View.GONE);
                for (int i = 0; i < bean.getGoodsFindGoods().getImgList().size(); i++) {
                    mBannerList.add(bean.getGoodsFindGoods().getImgList().get(i).getImgPath());
                }
                mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
                new Thread(new MyRunble()).start();
            }
        }
        mBinding.webView.loadDataWithBaseURL(null,
                bean.getGoodsFindGoods().getContent(),
                "text/html", "utf-8", null);
        mBinding.detailTitle.setText(bean.getGoodsFindGoods().getTitle());
        mBinding.detailSmallTitle.setText(bean.getGoodsFindGoods().getSmallTitle());
        if(TextUtils.isEmpty(bean.getGoodsFindGoods().getCommentNum())){
            mBinding.bottomCommenNum.setText("0");
        }else {
            mBinding.bottomCommenNum.setText(bean.getGoodsFindGoods().getCommentNum());
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
    /**
     * 获取详情页数据
     */
    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
    }


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

    @Override
    public void onFileIsCollect(String error) {
        isCollect = false;
        mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_nor);
        mBinding.bottomFavorNum.setText("0");
        Toast.makeText(this, "获取收藏信息失败", Toast.LENGTH_SHORT).show();
    }

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

    @Override
    public void onFileIsSnap(String error) {
        isSnap = false;
        mBinding.detailGoodTv.setTextColor(getResources().getColor(R.color.color_ACACAC));
        mBinding.detailGoodTv.setText("0");
        Toast.makeText(this, "获取点赞信息失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucComment(CommentBean commentBean) {
        mBinding.bottomCommenNum.setText(commentBean.getCount() + "");
    }

    @Override
    public void onFileComment(String error) {
        mBinding.bottomCommenNum.setText("0");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加收藏成功回调
     */
    @Override
    public void onSucCollectInsert(SuccessBean successBean) {
        EventBus.getDefault().post(FovalFragment.CollectResher);//刷新我的收藏列表
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        isCollect = true;
        mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_sel);
        mBinding.bottomFavorNum.setText(Integer.valueOf(mBinding.bottomFavorNum.getText().toString()) + 1 + "");
    }
    /**
     * 删除收藏成功回调
     */
    @Override
    public void onSucCollectDelete(SuccessBean successBean) {
        EventBus.getDefault().post(FovalFragment.CollectResher);//刷新我的收藏列表
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        isCollect = false;
        mBinding.bottomFavor.setImageResource(R.mipmap.nav_favor);
        mBinding.bottomFavorNum.setText(Integer.valueOf(mBinding.bottomFavorNum.getText().toString()) - 1 + "");
    }
    /**
     * 失败回调
     */
    @Override
    public void onFileCollectDelete(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
    /**
     * 添加点赞成功回调
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
     * 删除点赞成功回调
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

    /******************轮播图需要*********************/
    public class MyRunble implements Runnable {

        @Override
        public void run() {
            while (stopThread) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(100);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }

    /****
     * 跳转淘宝首页
     */
    public void openAliHomeWeb(String url) {
        AlibcShowParams alibcShowParams  = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");

        //yhhpass参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        AlibcTrade.show(this, new AlibcPage(url), alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                if (alibcTradeResult.resultType.equals(AlibcResultType.TYPECART)) {
                    //加购成功
                    Log.e("AlibcTradeSDK", "加购成功");
                } else if (alibcTradeResult.resultType.equals(AlibcResultType.TYPEPAY)) {
                    //支付成功
                    Log.e("AlibcTradeSDK", "支付成功,成功订单号为" + alibcTradeResult.payResult.paySuccessOrders);
                }
                Log.e("AlibcTradeSDK", "加购成功");
                Toast.makeText(FindDetailActivity.this, "进去了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
                Log.e("AlibcTradeSDK", "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    //获取评论列表
    @Subscribe
    public void commentResher(FindBus findBus){
        if(findBus != null){
            mBinding.bottomCommenNum.setText(findBus.getCount() + "");
        }
    }
}
