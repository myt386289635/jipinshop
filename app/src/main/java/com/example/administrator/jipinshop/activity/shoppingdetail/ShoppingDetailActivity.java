package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.ShoppingBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommonAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingmParameterAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.eventbus.CommenBus;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.ConcerBus;
import com.example.administrator.jipinshop.databinding.ActivityShopingDetailBinding;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.fragment.foval.FovalFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.AlignTextView;
import com.example.administrator.jipinshop.view.SaleProgressView;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.view.goodview.GoodView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
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
public class ShoppingDetailActivity extends BaseActivity implements ShoppingCommonAdapter.OnItemReply, ShoppingDetailView, ShareBoardDialog.onShareListener, View.OnClickListener, ShoppingCommonAdapter.OnGoodItem {

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
            }else if(msg.what == 101){
                mBinding.detailBottom.setVisibility(View.VISIBLE);
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean stopThread = true;

    //功能评分
    private List<TextView> mTextViews = new ArrayList<>();
    private List<AlignTextView> mAlignTextViews = new ArrayList<>();
    private List<SaleProgressView> mSaleProgressViews = new ArrayList<>();

    //产品参数
    private ShoppingmParameterAdapter mParameterAdapter;
    private List<ShoppingDetailBean.DataBean.GoodsDetailEntityBean.ParametersListBean> mParameterList;

    //用户评论
    private ShoppingCommonAdapter mCommonAdapter;
    private List<CommentBean.DataBean> mCommonList;

    //点赞
    private int[] usableHeightPrevious = {0};

    //分享面板
    private ShareBoardDialog mShareBoardDialog;
    //打开第三方天猫时，为了提高用户体验设计的
    private Dialog mDialog;
    //加载框
    private Dialog mDialogProgress;

    //商品id
    private String goodsId = "";

    private String goodsUrl = "";

    /**
     * 标志：是否收藏过此商品 false:没有
     */
    private boolean isCollect = false;

    /**
     * 标志：是否点赞过此商品  false:没有
     */
    private boolean isSnap = false;
    private int SnapNum = 0;//记录商品点赞数量

    /**
     * 标志开箱评测用户是否被关注过
     */
    private boolean isConcer = false;
    private String attentionUserId = "";//评测用户id

    /**
     * 父评论id
     */
    private String parentId = "0";
    private String toUserId = "0";//@回复的用户

    /**
     * 分享的图片
     */
    private String shareImage = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shoping_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setShoppingDetailView(this);
        EventBus.getDefault().register(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        initView();
    }

    private void initView() {

        goodsId = getIntent().getStringExtra("goodsId");//商品id

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

        //功能评分
        mTextViews.add(mBinding.itemProgressbar1TextDes);
        mTextViews.add(mBinding.itemProgressbar2TextDes);
        mTextViews.add(mBinding.itemProgressbar3TextDes);
        mTextViews.add(mBinding.itemProgressbar4TextDes);
        mTextViews.add(mBinding.itemProgressbar5TextDes);
        mAlignTextViews.add(mBinding.itemProgressbar1Text);
        mAlignTextViews.add(mBinding.itemProgressbar2Text);
        mAlignTextViews.add(mBinding.itemProgressbar3Text);
        mAlignTextViews.add(mBinding.itemProgressbar4Text);
        mAlignTextViews.add(mBinding.itemProgressbar5Text);
        mSaleProgressViews.add(mBinding.itemProgressbar1);
        mSaleProgressViews.add(mBinding.itemProgressbar2);
        mSaleProgressViews.add(mBinding.itemProgressbar3);
        mSaleProgressViews.add(mBinding.itemProgressbar4);
        mSaleProgressViews.add(mBinding.itemProgressbar5);

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
        mCommonAdapter.setOnGoodItem(this);
        mBinding.detailCommon.setAdapter(mCommonAdapter);

        //监听软键盘的弹出与收回
        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);
        mPresenter.initLine(mBinding.detailTitleContainer,mBinding.shopTv
                ,mBinding.shopView,mBinding.evaluationView,mBinding.commonView);
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mPresenter.setStatusBarHight(mBinding.statusBar2,this);

        mBinding.srcollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int left, int top, int oldLeft, int oldTop) {
                //控制头布局显示与消失情况
                float a = top;
                float b = a / 1000;
                float max = (float) Math.min(1, b * 2);
                mBinding.headContanier.setAlpha(max);
                //控制头布局滑动位置的变化
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
        //获取评论列表
        mPresenter.comment(goodsId,this.bindToLifecycle());
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
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
        super.onDestroy();
    }

    /**
     * 点击一级评论回复按钮
     *
     * @param pos
     */
    @Override
    public void onItemReply(int pos, TextView textView) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        parentId = mCommonList.get(pos).getCommentId();
        toUserId = "0";
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
        mBinding.keyEdit.setHint("回复"+mCommonList.get(pos).getUserNickname());
    }

    /**
     * 点击二级评论更多按钮
     *
     * @param pos
     */
    @Override
    public void onItemTwoReply(int pos) {
        //跳转到评论列表
        startActivity(new Intent(this, CommenListActivity.class)
                .putExtra("position",pos)
                .putExtra("id",goodsId)
                .putExtra("type","1")
        );
    }

    /**
     * 键盘弹出
     */
    @Override
    public void keyShow() {
        mBinding.detailBottom.setVisibility(View.INVISIBLE);
        mBinding.detailKeyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 软键盘收起
     */
    @Override
    public void keyHint() {
        mBinding.detailKeyLayout.setVisibility(View.GONE);
//        mHandler.sendEmptyMessageDelayed(101,100);
        mBinding.detailBottom.setVisibility(View.VISIBLE);
    }

    /**
     * 数据请求成功
     * @param recommendFragmentBean
     */
    @Override
    public void onSuccess(ShoppingDetailBean shoppingDetailBean) {
        if(shoppingDetailBean.getCode() == 0){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            goodsUrl = shoppingDetailBean.getData().getGoodsDetailEntity().getGoodsBuyLink();
            //初始值
            mBinding.detailName.setText(shoppingDetailBean.getData().getGoodsEntity().getGoodsName());
            mBinding.detailNewPrice.setText("¥" + shoppingDetailBean.getData().getGoodsEntity().getActualPrice());
            mBinding.detailOldPrice.setText("¥" + shoppingDetailBean.getData().getGoodsEntity().getOtherPrice());
            mBinding.detailOldPrice.setTv(true);
            mBinding.detailOldPrice.setColor(R.color.color_ACACAC);
            if(shoppingDetailBean.getData().getGoodsEntity().getSource()== 1){
                mBinding.detailOldPriceName.setText("京东价");
            }else  if(shoppingDetailBean.getData().getGoodsEntity().getSource() == 2){
                mBinding.detailOldPriceName.setText("淘宝价");
            }else  if(shoppingDetailBean.getData().getGoodsEntity().getSource() == 3){
                mBinding.detailOldPriceName.setText("天猫价");
            }
            //推荐理由
            String str = "<strong><font color='#21151515' >推荐理由：</font></strong>" + shoppingDetailBean.getData().getGoodsEntity().getRecommendReason();
            mBinding.detailReason.setText(Html.fromHtml(str));
            //优惠券
            mBinding.detaileCoupon.setText(shoppingDetailBean.getData().getGoodsEntity().getCouponPrice());
            mBinding.detailCouponDeci.setText("使用期限"+shoppingDetailBean.getData().getGoodsCouponsEntity().getValidStartTime()+"至"+shoppingDetailBean.getData().getGoodsCouponsEntity().getValidEndTime());
            //轮播图设置值
            if(shoppingDetailBean.getData().getGoodsEntity().getImgList().size() == 1){
                mBinding.viewPager.setVisibility(View.GONE);
                mBinding.detailPoint.setVisibility(View.GONE);
                mBinding.pagerImage.setVisibility(View.VISIBLE);
                GlideApp.loderImage(this,shoppingDetailBean.getData().getGoodsEntity().getImgList().get(0),mBinding.pagerImage,0,0);
            }else {
                mBinding.viewPager.setVisibility(View.VISIBLE);
                mBinding.detailPoint.setVisibility(View.VISIBLE);
                mBinding.pagerImage.setVisibility(View.GONE);
                for (int i = 0; i < shoppingDetailBean.getData().getGoodsEntity().getImgList().size(); i++) {
                    mBannerList.add(shoppingDetailBean.getData().getGoodsEntity().getImgList().get(i));
                }
                mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
                new Thread(new MyRunble()).start();
            }

//            //开箱评测头像
            if(shoppingDetailBean.getData().getEvaluationEntity() != null && !TextUtils.isEmpty(shoppingDetailBean.getData().getEvaluationEntity().getContent())){
                attentionUserId = shoppingDetailBean.getData().getEvaluationEntity().getUserId();
                GlideApp.loderCircleImage(this,shoppingDetailBean.getData().getEvaluationEntity().getUser().getAvatar(),mBinding.detailEvaluationImage,R.mipmap.rlogo ,0);
                mBinding.detailEvaluationName.setText(shoppingDetailBean.getData().getEvaluationEntity().getUser().getNickname());
                mBinding.detailEvaluationTime.setText(shoppingDetailBean.getData().getEvaluationEntity().getCreateTime());
                mBinding.detailEvaluationFans.setText("粉丝数："+shoppingDetailBean.getData().getEvaluationEntity().getUser().getFansCount());
                if (shoppingDetailBean.getData().getEvaluationEntity().getUser().getFollow() == 0) {
                    isConcer = false;
                    mBinding.contentAttention.setBackgroundResource(R.drawable.bg_attention);
                    mBinding.contentAttention.setText("+关注");
                    mBinding.contentAttention.setTextColor(getResources().getColor(R.color.color_E31436));
                }else {
                    isConcer = true;
                    mBinding.contentAttention.setBackgroundResource(R.drawable.bg_attentioned);
                    mBinding.contentAttention.setText("已关注");
                    mBinding.contentAttention.setTextColor(getResources().getColor(R.color.color_white));
                }
                mBinding.detailEvaluation.loadDataWithBaseURL(null,
                        shoppingDetailBean.getData().getEvaluationEntity().getContent(),
                        "text/html", "utf-8", null);
            }else {
                mBinding.detailEvaluationLine.setVisibility(View.GONE);
                mBinding.detailEvaluationTitle.setVisibility(View.GONE);
                mBinding.detailEvaluationHead.setVisibility(View.GONE);
                mBinding.detailEvaluation.setVisibility(View.GONE);
                mBinding.detailGood.setVisibility(View.GONE);
            }

            //产品参数
            if(shoppingDetailBean.getData().getGoodsDetailEntity().getParametersList() != null){
                mParameterList.addAll(shoppingDetailBean.getData().getGoodsDetailEntity().getParametersList());
                mParameterAdapter.notifyDataSetChanged();
            }
            //功能分析
            if(shoppingDetailBean.getData().getGoodsEntity().getScoreOptionsList() != null){
                mBinding.detailProgressContainer.setVisibility(View.VISIBLE);
                for (int i = 0; i < shoppingDetailBean.getData().getGoodsEntity().getScoreOptionsList().size(); i++) {
                    BigDecimal b = new BigDecimal(shoppingDetailBean.getData().getGoodsEntity().getScoreOptionsList().get(i).getScore());
                    double result = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//保留1位小数
                    mTextViews.get(i).setText(result + "分");
                    mAlignTextViews.get(i).setText(shoppingDetailBean.getData().getGoodsEntity().getScoreOptionsList().get(i).getName());
                    mSaleProgressViews.get(i).setTotalAndCurrentCount(100, Integer.valueOf(shoppingDetailBean.getData().getGoodsEntity().getScoreOptionsList().get(i).getScore()) * 10);
                }
                if(shoppingDetailBean.getData().getGoodsEntity().getScoreOptionsList().size() <= 4){
                    mBinding.itemProgressbar5Container.setVisibility(View.GONE);
                }
            }else {
                mBinding.detailProgressContainer.setVisibility(View.GONE);
            }
            //综合评分
            mBinding.itemScore.setText(shoppingDetailBean.getData().getGoodsEntity().getScore());

            //是否收藏过
            if(shoppingDetailBean.getData().getGoodsEntity().getCollect() == 1){
                isCollect = true;
                mBinding.detailFavor.setImageResource(R.mipmap.score_sel);
                mBinding.titleFavorImg.setImageResource(R.mipmap.score_sel);
            }else {
                isCollect = false;
                mBinding.detailFavor.setImageResource(R.mipmap.nav_favor);
                mBinding.titleFavorImg.setImageResource(R.mipmap.nav_favor_white);
            }
            //是否点赞过
            SnapNum = shoppingDetailBean.getData().getGoodsEntity().getVoteCount();
            if(shoppingDetailBean.getData().getGoodsEntity().getVote() == 1){
                isSnap = true;
                mBinding.detailGood.setText(shoppingDetailBean.getData().getGoodsEntity().getVoteCount() + "人喜欢");
                Drawable drawable= getResources().getDrawable(R.mipmap.like_sel);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
            }else {
                isSnap = false;
                mBinding.detailGood.setText(shoppingDetailBean.getData().getGoodsEntity().getVoteCount() + "人喜欢");
                Drawable drawable= getResources().getDrawable(R.mipmap.like_nor);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
            }

            if (mDialogProgress != null && mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }
        }else {
            if (mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }
            ToastUtil.show(shoppingDetailBean.getMsg());
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
        ToastUtil.show(error);
    }

    /**
     * 添加收藏成功
     * @param successBean
     */
    @Override
    public void onSucCollectInsert(SuccessBean successBean) {
        if(successBean.getCode() == 0){
            EventBus.getDefault().post(FovalFragment.CollectResher);//刷新我的收藏列表
            isCollect = true;
            mBinding.detailFavor.setImageResource(R.mipmap.score_sel);
            mBinding.titleFavorImg.setImageResource(R.mipmap.score_sel);
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 删除收藏成功
     */
    @Override
    public void onSucCollectDelete(SuccessBean successBean) {
        if(successBean.getCode() == 0){
            EventBus.getDefault().post(FovalFragment.CollectResher);
            isCollect = false;
            mBinding.detailFavor.setImageResource(R.mipmap.nav_favor);
            mBinding.titleFavorImg.setImageResource(R.mipmap.nav_favor_white);
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 添加收藏、删除收藏、添加点赞、删除点赞 失败回调
     */
    @Override
    public void onFileCollectDelete(String error) {
        ToastUtil.show(error);
    }

    /**
     * 添加赞过 成功回调
     */
    @Override
    public void onSucSnapInsert(View view ,VoteBean successBean) {
        if(successBean.getCode() == 0){
            isSnap = true;
            SnapNum = SnapNum + 1;
            mBinding.detailGood.setText(SnapNum + "人喜欢");
            Drawable drawable= getResources().getDrawable(R.mipmap.like_sel);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 删除赞过 成功回调
     */
    @Override
    public void onSucSnapDelete(SuccessBean successBean) {
        if(successBean.getCode() == 0){
            isSnap = false;
            SnapNum = SnapNum - 1;
            mBinding.detailGood.setText(SnapNum + "人喜欢");
            Drawable drawable= getResources().getDrawable(R.mipmap.like_nor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 获取评论列表 成功回调
     */
    @Override
    public void onSucComment(CommentBean commentBean) {
        if(commentBean.getData() != null && commentBean.getData().size() != 0){
            mBinding.detailCommon.setVisibility(View.VISIBLE);
            mBinding.detailCommonTotle.setVisibility(View.VISIBLE);
            mBinding.detailCommentLayout.setVisibility(View.GONE);
            mBinding.detailSend.setVisibility(View.VISIBLE);
            mCommonList.clear();
            mCommonList.addAll(commentBean.getData());
            mCommonAdapter.notifyDataSetChanged();
            if(commentBean.getTotal() > 3){
                mBinding.detailCommonTotle.setText("查看全部"+commentBean.getTotal()+"条评论");
            }else {
                mBinding.detailCommonTotle.setText("查看更多");
            }
        }else {
            mBinding.detailCommon.setVisibility(View.GONE);
            mBinding.detailCommonTotle.setVisibility(View.GONE);
            mBinding.detailCommentLayout.setVisibility(View.VISIBLE);
            mBinding.detailSend.setVisibility(View.GONE);
        }
    }

    /**
     * 获取评论列表 失败回调
     */
    @Override
    public void onFileComment(String error) {
        mBinding.detailCommon.setVisibility(View.GONE);
        mBinding.detailCommonTotle.setVisibility(View.GONE);
        mBinding.detailCommentLayout.setVisibility(View.VISIBLE);
        mBinding.detailSend.setVisibility(View.GONE);
        ToastUtil.show(error);
    }

    /**
     * 评论成功
     */
    @Override
    public void onSucCommentInsert(SuccessBean successBean) {
        mPresenter.comment(goodsId,this.bindToLifecycle());
        mBinding.keyEdit.setText("");
        hintKey();
        if (mDialogProgress != null && mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
        }
    }

    /**
     * 评论失败
     */
    @Override
    public void onFileCommentInsert(String error) {
        if (mDialogProgress != null && mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 评论点赞成功回调
     */
    @Override
    public void onSucCommentSnapIns(int position,VoteBean successBean) {
        if (successBean.getCode() == 0){
            mCommonList.get(position).setVote(1);
            BigDecimal bigDecimal = new BigDecimal(mCommonList.get(position).getVoteCount());
            int num = bigDecimal.intValue();
            mCommonList.get(position).setVoteCount((num + 1));
            mCommonAdapter.notifyItemChanged(position);
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 评论删除点赞成功回调
     */
    @Override
    public void onSucCommentSnapDel(int position,SuccessBean successBean) {
        if(successBean.getCode() == 0){
            mCommonList.get(position).setVote(0);
            BigDecimal bigDecimal = new BigDecimal(mCommonList.get(position).getVoteCount());
            int num = bigDecimal.intValue();
            mCommonList.get(position).setVoteCount((num - 1));
            mCommonAdapter.notifyItemChanged(position);
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 取消关注成功
     */
    @Override
    public void concerDelSuccess(SuccessBean successBean) {
        if(successBean.getCode() == 0){
            isConcer = false;
            mBinding.contentAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.contentAttention.setText("+关注");
            mBinding.contentAttention.setTextColor(getResources().getColor(R.color.color_E31436));
            String num = mBinding.detailEvaluationFans.getText().toString().replace("粉丝数：" , "");
            mBinding.detailEvaluationFans.setText("粉丝数："+ (Integer.valueOf(num) - 1));
            EventBus.getDefault().post(new ConcerBus(CommonEvaluationFragment.REFERSH,0,(Integer.valueOf(num) - 1) + "",attentionUserId));//刷新评测首页
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }
    /**
     * 添加关注成功
     */
    @Override
    public void concerInsSuccess(SuccessBean successBean) {
        if(successBean.getCode() == 0){
            isConcer = true;
            mBinding.contentAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.contentAttention.setText("已关注");
            mBinding.contentAttention.setTextColor(getResources().getColor(R.color.color_white));
            String num = mBinding.detailEvaluationFans.getText().toString().replace("粉丝数：" , "");
            mBinding.detailEvaluationFans.setText("粉丝数："+ (Integer.valueOf(num) + 1));
            EventBus.getDefault().post(new ConcerBus(CommonEvaluationFragment.REFERSH,1,(Integer.valueOf(num) + 1) + "",attentionUserId));//刷新评测首页
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
      }

    /**
     * 登陆后刷新页面状态
     */
    @Override
    public void pagerStateSuccess(PagerStateBean pagerStateBean) {
        if (pagerStateBean.getFollow() == 0) {
            isConcer = false;
            mBinding.contentAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.contentAttention.setText("+关注");
            mBinding.contentAttention.setTextColor(getResources().getColor(R.color.color_E31436));
        }else {
            isConcer = true;
            mBinding.contentAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.contentAttention.setText("已关注");
            mBinding.contentAttention.setTextColor(getResources().getColor(R.color.color_white));
        }
        if(pagerStateBean.getCollect() == 1){
            isCollect = true;
            mBinding.detailFavor.setImageResource(R.mipmap.score_sel);
            mBinding.titleFavorImg.setImageResource(R.mipmap.score_sel);
        }else {
            isCollect = false;
            mBinding.detailFavor.setImageResource(R.mipmap.nav_favor);
            mBinding.titleFavorImg.setImageResource(R.mipmap.nav_favor_white);
        }
        if(pagerStateBean.getVote() == 1){
            isSnap = true;
            mBinding.detailGood.setText(SnapNum + "人喜欢");
            Drawable drawable= getResources().getDrawable(R.mipmap.like_sel);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
        }else {
            isSnap = false;
            mBinding.detailGood.setText(SnapNum + "人喜欢");
            Drawable drawable= getResources().getDrawable(R.mipmap.like_nor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
        }
    }

    /**
     * 分享
     *
     * @param share_media
     */
    @Override
    public void share(SHARE_MEDIA share_media) {
//        new ShareUtils(this, share_media)
//                .shareWeb(this, RetrofitModule.UP_BASE_URL + "share/info-tab.html?goodsId=" + goodsId, goodsName, goodsName, shareImage, R.mipmap.share_logo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
            case R.id.detail_back:
                hintKey();
                finish();
                break;
            case R.id.title_favor:
            case R.id.detail_favor:
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                }else{
                    if(isCollect){
                        //收藏过了
                        mPresenter.collectDelete(goodsId,this.bindToLifecycle());
                    }else {
                        //没有收藏
                        mPresenter.collectInsert(goodsId,this.bindToLifecycle());
                    }
                }
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
                if(mBinding.detailEvaluationLine.getVisibility() == View.VISIBLE ){
                    mBinding.srcollView.scrollTo(0, mBinding.detailEvaluationLine.getTop());
                }else {
                    mBinding.srcollView.scrollTo(0, mBinding.detailHeadLine.getTop());//当开箱评测没有时
                }
                mPresenter.initTitleLayout(this, mBinding.commonTv,  mBinding.commonView, mBinding.shopTv, mBinding.shopView, mBinding.evaluationTv, mBinding.evaluationView);
                hintKey();
                break;
            case R.id.detail_good:
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                }else{
                    if(isSnap){
                        //点赞过了
                        mPresenter.snapDelete("1",0,goodsId,this.bindToLifecycle());
                    }else {
                        //没有点赞
                        mPresenter.snapInsert("1",0,view,goodsId,this.bindToLifecycle());
                    }
                }
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
            case R.id.detail_couponImg:
            case R.id.detail_buy:
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                openAliHomeWeb(goodsUrl);
                break;
            case R.id.key_text:
                //发送按钮
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    ToastUtil.show("请输入评论");
                    return;
                }
                mDialogProgress = (new ProgressDialogView()).createLoadingDialog(ShoppingDetailActivity.this, "正在加载...");
                mDialogProgress.show();
                mPresenter.commentInsert(goodsId,toUserId,mBinding.keyEdit.getText().toString(),parentId,this.bindToLifecycle());
                break;
            case R.id.detail_commonTotle:
                //跳转到评论列表
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position",-1)
                        .putExtra("id",goodsId)
                        .putExtra("type","1")
                );
                break;
            case R.id.content_attention:
                //关注
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                }else{
                    if(isConcer){
                        //关注过了
                        mPresenter.concernDelete(attentionUserId,this.bindToLifecycle());
                    }else {
                        //没有关注
                        mPresenter.concernInsert(attentionUserId,this.bindToLifecycle());
                    }
                }
                break;
            case R.id.in_clude:
                if(mBinding.inClude.errorTitle.getText().toString().equals("网络出错")){
                    mDialogProgress = (new ProgressDialogView()).createLoadingDialog(ShoppingDetailActivity.this, "正在加载...");
                    mDialogProgress.show();
                    mPresenter.getDate(goodsId,this.<ShoppingDetailBean>bindToLifecycle());
                }
                break;
            case R.id.detail_send:
            case R.id.detail_commentTv:
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                parentId = "0";
                toUserId = "0";
                mBinding.keyEdit.requestFocus();
                showKeyboard(true);
                break;
        }
    }

    /**
     * 评论点赞与取消点赞
     */
    @Override
    public void onGood(int flag, int position) {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        }else{
            if(flag == 1){
                //取消点赞
                mPresenter.snapDelete("5",position,mCommonList.get(position).getCommentId(),this.bindToLifecycle());
            }else {
                //点赞
                mPresenter.snapInsert("5",position,null,mCommonList.get(position).getCommentId(),this.bindToLifecycle());
            }
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

    public void hintKey() {
        if (mBinding.detailKeyLayout.getVisibility() == View.VISIBLE) {
            if (mImm.isActive()) {
                // 如果开启
                mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
//                mBinding.detailKeyLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mBinding.detailKeyLayout.getVisibility() == View.VISIBLE && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = mBinding.detailKeyLayout;
            if (isHideInput(view, ev)) {
                hintKey();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof RelativeLayout)) {
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

    /****
     * 跳转淘宝首页
     */
    public void openAliHomeWeb(String url) {
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
                ToastUtil.show("进去了");
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

    //获取评论列表
    @Subscribe
    public void commentResher(CommenBus commenBus){
        if(commenBus != null && commenBus.getTag().equals(CommenListActivity.commentResher)){
            mPresenter.comment(goodsId,this.bindToLifecycle());
        }
    }

    /**
     * 登陆时刷新 关注 收藏 点赞 评论
     */
    @Subscribe
    public void refreshPage(CommonEvaluationBus commonEvaluationBus){
        if(commonEvaluationBus != null && commonEvaluationBus.getRefersh().equals(LoginActivity.refresh)){
            mPresenter.pagerState(goodsId,this.bindToLifecycle());
            mPresenter.comment(goodsId,this.bindToLifecycle());
        }
    }
}
