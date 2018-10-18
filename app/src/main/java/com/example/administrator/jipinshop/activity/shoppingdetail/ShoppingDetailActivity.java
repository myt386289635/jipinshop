package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.adapter.ShoppingBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommonAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingQualityAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingmParameterAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.ActivityShopingDetailBinding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.example.administrator.jipinshop.view.goodview.GoodView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe 商品详情页
 * (该页需要后期优化。。。。: 优化方案：
 * 1、 使用一个recycelrView写,n个item，注意不要嵌套其他滑动布局，否则会卡顿)
 */
@SuppressWarnings("all")
public class ShoppingDetailActivity extends BaseActivity implements ShoppingCommonAdapter.OnItemReply, ShoppingDetailView, ShareBoardDialog.onShareListener, View.OnClickListener {

    @Inject
    ShoppingDetailPresenter mPresenter;
    private ActivityShopingDetailBinding mBinding;

    //banner
    private ShoppingBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 100){
                if (mBinding.viewPager != null) {
                    mBinding.viewPager.setCurrentItem(mBinding.viewPager.getCurrentItem() + 1);
                }
            }else if(msg.what == 1){
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.detailEvaluation.getLayoutParams();
                layoutParams.height = (int) (msg.arg1 * getResources().getDisplayMetrics().density);
                mBinding.detailEvaluation.setLayoutParams(layoutParams);
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean stopThread = true;


    //品质保证、售后服务
    private ShoppingQualityAdapter mQualityAdapter;
    private List<String> mQualityList;

    //产品参数
    private ShoppingmParameterAdapter mParameterAdapter;
    private List<String> mParameterList;

    //用户评论
    private ShoppingCommonAdapter mCommonAdapter;
    private List<String> mCommonList;

    //点赞
    private GoodView mGoodView;
    private int[] usableHeightPrevious = {0};

    //分享面板
    private ShareBoardDialog mShareBoardDialog;
    //打开第三方天猫时，为了提高用户体验设计的
    private Dialog mDialog;
    //加载框
    private Dialog mDialogProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shoping_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setShoppingDetailView(this);
        initView();
    }

    private void initView() {
        mDialogProgress = (new ProgressDialogView()).createLoadingDialog(ShoppingDetailActivity.this, "正在加载...");
        mDialogProgress.show();

        mBannerAdapter = new ShoppingBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBinding.viewPager.setAdapter(mBannerAdapter);
        mBinding.viewPager.setCurrentItem(mBannerList.size() * 10);

        //品质保证
        mQualityList = new ArrayList<>();
        mQualityAdapter = new ShoppingQualityAdapter(mQualityList, this);
        mBinding.detailQuality.setAdapter(mQualityAdapter);
        mBinding.detailQuality.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //售后服务
        mBinding.detailService.setAdapter(mQualityAdapter);
        mBinding.detailService.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //产品参数
        mParameterList = new ArrayList<>();
        mParameterAdapter = new ShoppingmParameterAdapter(mParameterList, this);
        mBinding.detailParameter.setAdapter(mParameterAdapter);
        mBinding.detailParameter.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //开箱评测
//        mPresenter.initWebView(mBinding.detailEvaluation);
//        mBinding.detailEvaluation.addJavascriptInterface(new WebViewJavaScriptFunction(), "android");
//        mBinding.detailEvaluation.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mBinding.detailEvaluation.loadUrl("javascript:window.android.getBodyHeight(document.body.scrollHeight)");
//            }
//        });
//        //判断页面加载过程
//        mBinding.detailEvaluation.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {// 网页加载完成
//                    if (mDialogProgress != null && mDialogProgress.isShowing()) {
//                        mDialogProgress.dismiss();
//                    }
//                }
//            }
//        });

        mBinding.detailEvaluationImageview.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mBinding.detailEvaluationImageview.setZoomEnabled(false);

        //用户评论
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };
        mBinding.detailCommon.setLayoutManager(layoutManager1);
        mBinding.detailCommon.setFocusable(false);
        mCommonList = new ArrayList<>();
        mCommonAdapter = new ShoppingCommonAdapter(mCommonList, this);
        mCommonAdapter.setOnItemReply(this);
        mBinding.detailCommon.setAdapter(mCommonAdapter);

        mGoodView = new GoodView(this);
        //监听软键盘的弹出与收回
        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);

        mBinding.srcollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                Log.d("ShoppingDetailActivity", "view.getScrollY():" + view.getScrollY());
//                Log.d("ShoppingDetailActivity", "mBinding.detailHeadLine.getTop():" + mBinding.detailHeadLine.getTop());
                if(view.getScrollY() >= mBinding.detailEvaluationLine.getTop()){
                    mPresenter.initTitleLayout(ShoppingDetailActivity.this, mBinding.commonTv,  mBinding.commonView, mBinding.shopTv, mBinding.shopView, mBinding.evaluationTv, mBinding.evaluationView);
                }else if(view.getScrollY() >= mBinding.detailHeadLine.getTop()){
                    mPresenter.initTitleLayout(ShoppingDetailActivity.this, mBinding.evaluationTv, mBinding.evaluationView, mBinding.shopTv, mBinding.shopView, mBinding.commonTv,  mBinding.commonView);
                }else {
                    mPresenter.initTitleLayout(ShoppingDetailActivity.this, mBinding.shopTv, mBinding.shopView, mBinding.evaluationTv, mBinding.evaluationView, mBinding.commonTv, mBinding.commonView);
                }
            }
        });
        //网络请求数据
        mPresenter.getDate(this.<RecommendFragmentBean>bindToLifecycle());
    }


    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
        stopThread = false;
        mHandler.removeCallbacksAndMessages(null);
        //防止webView内存溺出
        if (mBinding.detailEvaluation != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mBinding.detailEvaluation.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBinding.detailEvaluation);
            }
            mBinding.detailEvaluation.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mBinding.detailEvaluation.getSettings().setJavaScriptEnabled(false);
            mBinding.detailEvaluation.clearHistory();
            mBinding.detailEvaluation.clearView();
            mBinding.detailEvaluation.removeAllViews();
            mBinding.detailEvaluation.destroy();
        }
        mBinding.detailEvaluationImageview.recycle();
        super.onDestroy();
    }

    /**
     * 点击一级评论回复按钮
     *
     * @param pos
     */
    @Override
    public void onItemReply(int pos, TextView textView) {
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
    }

    /**
     * 点击二级评论回复按钮
     *
     * @param pos
     */
    @Override
    public void onItemTwoReply(int pos) {
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
    }

    /**
     * 键盘弹出
     */
    @Override
    public void keyShow() {
        mBinding.detailKeyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 软键盘收起
     */
    @Override
    public void keyHint() {
        mBinding.detailKeyLayout.setVisibility(View.GONE);
    }

    /**
     * 数据请求成功
     * @param recommendFragmentBean
     */
    @Override
    public void onSuccess(RecommendFragmentBean recommendFragmentBean) {
        if(recommendFragmentBean.getCode() == 200){
            //轮播图设置值
            mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
            new Thread(new MyRunble()).start();

            //开箱评测头像
            ImageManager.displayCircleImage(MyApplication.imag,mBinding.detailEvaluationImage,0,0);
            mBinding.detailEvaluation.loadUrl("http://192.168.5.136:8080/ueditor/161628719039.html");
            String testUrl ="http://wimg.spriteapp.cn/ugc/2018/09/22/5ba60aff63dfd_1.jpg";
            //下载图片保存到本地
            Glide.with(this)
                    .load(testUrl).downloadOnly(new SimpleTarget<File>() {
                @Override
                public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                    // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                    mBinding.detailEvaluationImageview.setImage(ImageSource.uri(Uri.fromFile(resource)));
                    if (mDialogProgress != null && mDialogProgress.isShowing()) {
                        mDialogProgress.dismiss();
                    }
                }
            });

            //显示评论
            for (int i = 0; i < 1 + getIntent().getIntExtra("pos",0); i++) {
                mCommonList.add("");
            }
            mCommonAdapter.notifyDataSetChanged();

            for (int i = 0; i < 1 + getIntent().getIntExtra("pos",0); i++) {
                mParameterList.add("");
            }
            mParameterAdapter.notifyDataSetChanged();

            for (int i = 0; i < 1 + getIntent().getIntExtra("pos",0); i++) {
                mQualityList.add("");
            }
            mQualityAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 数据请求失败
     * @param recommendFragmentBean
     */
    @Override
    public void onFile(String error) {
        if (mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
        }
    }

    /**
     * 分享
     *
     * @param share_media
     */
    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, "https://www.baidu.com", "测试", "测试而已", "", R.mipmap.ic_launcher_round);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
                hintKey();
                finish();
                break;
            case R.id.detail_favor:
                break;
            case R.id.head_shop:
                //商品
                mBinding.srcollView.scrollTo(0, 0);
                mPresenter.initTitleLayout(this, mBinding.shopTv, mBinding.shopView, mBinding.evaluationTv, mBinding.evaluationView, mBinding.commonTv, mBinding.commonView);
                hintKey();
                break;
            case R.id.head_evaluation:
                //评测
                mBinding.srcollView.scrollTo(0, mBinding.detailHeadLine.getTop());
                mPresenter.initTitleLayout(this, mBinding.evaluationTv, mBinding.evaluationView, mBinding.shopTv, mBinding.shopView, mBinding.commonTv,  mBinding.commonView);
                hintKey();
                break;
            case R.id.head_common:
                //评价
                mBinding.srcollView.scrollTo(0, mBinding.detailEvaluationLine.getTop());
                mPresenter.initTitleLayout(this, mBinding.commonTv,  mBinding.commonView, mBinding.shopTv, mBinding.shopView, mBinding.evaluationTv, mBinding.evaluationView);
                hintKey();
                break;
            case R.id.detail_good:
                mGoodView.setText("+1");
                mGoodView.setTextColor(getResources().getColor(R.color.color_E31436));
                mGoodView.show(view);
                break;
            case R.id.detail_share:
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = new ShareBoardDialog();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }

                break;
            case R.id.detail_buy:
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                openAliHomeWeb();
                break;
            case R.id.key_text:
                //发送按钮
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    Toast.makeText(this, "请输入评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBinding.keyEdit.setText("");
                hintKey();
                break;
            case R.id.detail_commonTotle:
                //跳转到评论列表
                startActivity(new Intent(this, CommenListActivity.class));
                break;
            case R.id.content_attention:
                //关注
                break;
        }
    }


    /******************轮播图需要*********************/
    public class MyRunble implements Runnable {

        @Override
        public void run() {
            while (stopThread) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(100);
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hideSoftInput(view);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void hideSoftInput(View view) {
        if (mImm.isActive()) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public void hintKey() {
        if (mBinding.detailKeyLayout.getVisibility() == View.VISIBLE) {
            if (mImm.isActive()) {
                // 如果开启
                mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
                mBinding.detailKeyLayout.setVisibility(View.GONE);
            }
        }
    }

    /****
     * 跳转淘宝首页
     */
    public void openAliHomeWeb() {
        String url = "https://item.taobao.com/item.htm?id=577514743347&ali_refid=a3_430673_1006:1122566292:N:%E8%BF%9E%E8%A1%A3%E8%A3%99:3414b5a7e38760d60ae26d74b9235683&ali_trackid=1_3414b5a7e38760d60ae26d74b9235683&spm=a2e15.8261149.07626516002.2";
        Boolean tb = true;

        AlibcShowParams alibcShowParams  = new AlibcShowParams(OpenType.Native, false);
        if(tb){
            //淘宝协议
            alibcShowParams.setClientType("taobao_scheme");
        }else {
            //天猫协议
            alibcShowParams.setClientType("tmall_scheme");
        }

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
                Toast.makeText(ShoppingDetailActivity.this, "进去了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
                Log.e("AlibcTradeSDK", "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
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
            mHandler.sendMessage(msg);

        }
    }
}
