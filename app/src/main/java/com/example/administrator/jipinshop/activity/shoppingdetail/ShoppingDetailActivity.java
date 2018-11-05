package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.adapter.ShoppingBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommonAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingQualityAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingmParameterAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityShopingDetailBinding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.example.administrator.jipinshop.view.goodview.GoodView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

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


    //品质保证
    private ShoppingQualityAdapter mQualityAdapter;
    private List<String> mQualityList;

    //售后服务
    private ShoppingQualityAdapter mSreverAdapter;
    private List<String> mSreverList;

    //产品参数
    private ShoppingmParameterAdapter mParameterAdapter;
    private List<ShoppingDetailBean.GoodsRankdetailEntityBean.ParametersListBean> mParameterList;

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

    //商品id
    private String goodsId = "";
    private String goodsName = "";
    private String priceNow = "";
    private String priceOld = "";
    private String price = "";
    private String sourceStatus = "";

    private String goodsUrl = "";

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

        goodsId = getIntent().getStringExtra("goodsId");//商品id
        goodsName = getIntent().getStringExtra("goodsName");
        priceNow = getIntent().getStringExtra("priceNow");
        priceOld = getIntent().getStringExtra("priceOld");
        price = getIntent().getStringExtra("price");
        sourceStatus = getIntent().getStringExtra("state");//来源状态

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
        mSreverList = new ArrayList<>();
        mSreverAdapter = new ShoppingQualityAdapter(mSreverList,this);
        mBinding.detailService.setAdapter(mSreverAdapter);
        mBinding.detailService.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //产品参数
        mParameterList = new ArrayList<>();
        mParameterAdapter = new ShoppingmParameterAdapter(mParameterList, this);
        mBinding.detailParameter.setAdapter(mParameterAdapter);
        mBinding.detailParameter.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //开箱评测
        mPresenter.initWebView(mBinding.detailEvaluation);
        mBinding.detailEvaluation.addJavascriptInterface(new WebViewJavaScriptFunction(), "android");
        mBinding.detailEvaluation.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBinding.detailEvaluation.loadUrl("javascript:window.android.getBodyHeight(document.body.scrollHeight)");
            }
        });
        //判断页面加载过程
        mBinding.detailEvaluation.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {// 网页加载完成
                    if (mDialogProgress != null && mDialogProgress.isShowing()) {
                        mDialogProgress.dismiss();
                    }
                }
            }
        });

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
        mPresenter.getDate(goodsId,this.<ShoppingDetailBean>bindToLifecycle());
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
    public void onSuccess(ShoppingDetailBean shoppingDetailBean) {
        if(shoppingDetailBean.getCode() == 200){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            goodsUrl = shoppingDetailBean.getGoodsRankdetailEntity().getGoodsBuyLink();
            //初始值
            if(!TextUtils.isEmpty(goodsName)){
                mBinding.detailName.setText(goodsName);
            }
            if(!TextUtils.isEmpty(priceNow)){
                mBinding.detailNewPrice.setText("券后价：¥" + priceNow);
            }
            if(!TextUtils.isEmpty(priceOld)){
                mBinding.detailOldPrice.setText("¥" + priceOld);
            }
            if(!TextUtils.isEmpty(sourceStatus)){
                if(sourceStatus.equals("1")){
                    mBinding.detailOldPriceName.setText("京东：");
                }else  if(sourceStatus.equals("2")){
                    mBinding.detailOldPriceName.setText("淘宝：");
                }else  if(sourceStatus.equals("3")){
                    mBinding.detailOldPriceName.setText("天猫：");
                }
            }
            if(!TextUtils.isEmpty(price)){
                mBinding.detaileCouponTitle.setText(price + "元独家优惠券");
            }
            //轮播图设置值
            for (int i = 0; i < shoppingDetailBean.getGoodsRankdetailEntity().getImgList().size(); i++) {
                mBannerList.add(shoppingDetailBean.getGoodsRankdetailEntity().getImgList().get(i).getImgPath());
            }
            mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
            new Thread(new MyRunble()).start();

            //开箱评测头像
            if(shoppingDetailBean.getGoodsRankdetailEntity().getGoodsEvalWayEntity() != null){
                ImageManager.displayCircleImage(MyApplication.imag,mBinding.detailEvaluationImage,
                        0,0);
                mBinding.detailEvaluation.loadDataWithBaseURL(null,
                        shoppingDetailBean.getGoodsRankdetailEntity().getGoodsEvalWayEntity().getContent(),
                        "text/html", "utf-8", null);
            }else {
                if (mDialogProgress.isShowing()) {
                    mDialogProgress.dismiss();
                }
                mBinding.detailEvaluationLine.setVisibility(View.GONE);
                mBinding.detailEvaluationTitle.setVisibility(View.GONE);
                mBinding.detailEvaluationHead.setVisibility(View.GONE);
                mBinding.detailEvaluation.setVisibility(View.GONE);
            }

            for (int i = 0; i < 1 + getIntent().getIntExtra("pos",0); i++) {
                mCommonList.add("");
            }
            mCommonAdapter.notifyDataSetChanged();

            if(shoppingDetailBean.getGoodsRankdetailEntity().getParametersList() != null){
                mParameterList.addAll(shoppingDetailBean.getGoodsRankdetailEntity().getParametersList());
                mParameterAdapter.notifyDataSetChanged();
            }

            if (shoppingDetailBean.getGoodsRankdetailEntity().getQualityList() != null) {
                for (ShoppingDetailBean.GoodsRankdetailEntityBean.QualityListBean qualityListBean : shoppingDetailBean.getGoodsRankdetailEntity().getQualityList()) {
                    mQualityList.add(qualityListBean.getName());
                }
                mQualityAdapter.notifyDataSetChanged();
            }

            if (shoppingDetailBean.getGoodsRankdetailEntity().getServiceList()!= null) {
                for (ShoppingDetailBean.GoodsRankdetailEntityBean.ServiceListBean serviceListBean : shoppingDetailBean.getGoodsRankdetailEntity().getServiceList()) {
                    mSreverList.add(serviceListBean.getName());
                }
                mSreverAdapter.notifyDataSetChanged();
            }

        }else {
            if (mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }
            Toast.makeText(this, shoppingDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
            initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
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
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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
                openAliHomeWeb(goodsUrl,sourceStatus);
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
            case R.id.in_clude:
                if(mBinding.inClude.errorTitle.getText().toString().equals("网络出错")){
                    mDialogProgress = (new ProgressDialogView()).createLoadingDialog(ShoppingDetailActivity.this, "正在加载...");
                    mDialogProgress.show();
                    mPresenter.getDate(goodsId,this.<ShoppingDetailBean>bindToLifecycle());
                }
                break;
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
    public void openAliHomeWeb(String url, String tb ) {
        AlibcShowParams alibcShowParams  = new AlibcShowParams(OpenType.Native, false);
//        if(tb.equals("2")){
            //淘宝协议
            alibcShowParams.setClientType("taobao_scheme");
//        }else if(tb.equals("3")){
//            //天猫协议
//            alibcShowParams.setClientType("tmall_scheme");
//        }

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

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }
}
