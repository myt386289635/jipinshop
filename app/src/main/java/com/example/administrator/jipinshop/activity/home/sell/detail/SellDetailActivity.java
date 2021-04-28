package com.example.administrator.jipinshop.activity.home.sell.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingImageAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingRecommendAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingUserLikeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.CommenBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.TBShopDetailBus;
import com.example.administrator.jipinshop.databinding.ActivitySellDetailBinding;
import com.example.administrator.jipinshop.fragment.foval.goods.FovalGoodsFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.textview.CenteredImageSpan;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/4/27
 * @Describe 买多少返多少 详情页
 */
public class SellDetailActivity extends BaseActivity implements View.OnClickListener, SellDetailView, ShoppingUserLikeAdapter.OnItem {

    @Inject
    SellDetailPresenter mPresenter;

    private ActivitySellDetailBinding mBinding;
    private String goodsId = "";//商品id
    private String source = "2";//商品来源:1京东,2淘宝，4拼多多 默认淘宝详情
    private Dialog mDialog;
    private Dialog mProgressDialog;
    //是否开启底部会员弹框
    private Boolean isStart = false;
    private String level = "0";//用户身份
    private String UpFee = "";
    private String fee = "";
    //banner
    private NoPageBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    //高佣推荐
    private List<SimilerGoodsBean.DataBean> mRecommendList;
    private ShoppingRecommendAdapter mRecommendAdapter;
    //商品详情
    private List<String> mDetailList;
    private ShoppingImageAdapter mImageAdapter;
    //猜你喜欢
    private List<SimilerGoodsBean.DataBean> mUserLikeList;
    private ShoppingUserLikeAdapter mLikeAdapter;
    private boolean isCollect = false;//标志：是否收藏过此商品 false:没有
    private int goodsType = 2;//1是极品城  2是淘宝
    private String money = "0.00";//总共要省的钱
    //查看全部评论时的跳转Url
    private String commenUrl = "";
    //刷新页面
    private Boolean buyRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sell_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("source"))){
            source = getIntent().getStringExtra("source");//商品来源
        }
        goodsId = getIntent().getStringExtra("otherGoodsId");//商品id
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mBinding.swipeTarget.setOnScrollListener(scrollY -> {
            if (scrollY >= mBinding.detailName.getTop()){
                if (isStart){
                    if (mBinding.detailMemberContainer.getVisibility() != View.VISIBLE)
                        mBinding.detailMemberContainer.setVisibility(View.VISIBLE);
                }else {
                    if (mBinding.detailMemberContainer.getVisibility() != View.GONE)
                        mBinding.detailMemberContainer.setVisibility(View.GONE);
                }
            }else {
                if (mBinding.detailMemberContainer.getVisibility() != View.GONE)
                    mBinding.detailMemberContainer.setVisibility(View.GONE);
            }
        });

        //banner
        mBannerAdapter = new NoPageBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBannerAdapter.setImgCenter(true);
        mBinding.viewPager.setAdapter(mBannerAdapter);

        //高佣推荐
        mRecommendList = new ArrayList<>();
        mBinding.detailRecommend.setLayoutManager(new GridLayoutManager(this,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecommendAdapter = new ShoppingRecommendAdapter(mRecommendList,this);
        mBinding.detailRecommend.setAdapter(mRecommendAdapter);

        //商品详情
        mDetailList = new ArrayList<>();
        mImageAdapter = new ShoppingImageAdapter(mDetailList,this);
        mBinding.detailDec.setLayoutManager(new LinearLayoutManager(this));
        mBinding.detailDec.setNestedScrollingEnabled(false);
        mBinding.detailDec.setAdapter(mImageAdapter);

        //猜你喜欢
        mBinding.detailUserLike.setLayoutManager(new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        });
        mUserLikeList = new ArrayList<>();
        mLikeAdapter = new ShoppingUserLikeAdapter(mUserLikeList,this);
        mLikeAdapter.setOnItem(this);
        mBinding.detailUserLike.setAdapter(mLikeAdapter);

        //模拟数据
        mProgressDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mProgressDialog.show();
        mPresenter.tbGoodsDetail(1,goodsId,source,this.bindToLifecycle());
        mPresenter.listLikeGoods(goodsId,this.bindToLifecycle());
        if (source.equals("2")){//只有淘宝商品有猜你喜欢和评论
            mBinding.detailUserLikeImage.setVisibility(View.VISIBLE);
            mBinding.detailUserLike.setVisibility(View.VISIBLE);
            Map<String,String> map =  DeviceUuidFactory.getIdfa(this);
            mPresenter.listSimilerGoods(map,this.bindToLifecycle());
            mPresenter.getFeedback(goodsId,this.bindToLifecycle());
        }else {
            mBinding.detailUserLikeImage.setVisibility(View.GONE);
            mBinding.detailUserLike.setVisibility(View.GONE);
            mBinding.detailLine.setVisibility(View.GONE);
            mBinding.detailCommentNum.setVisibility(View.GONE);
            mBinding.detailCommentGo.setVisibility(View.GONE);
            mBinding.detailCommentLayout.setVisibility(View.GONE);
            mBinding.detailGoShop.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(TBShoppingDetailBean bean) {
        mBinding.setDate(bean.getData());
        mBinding.executePendingBindings();
        mBinding.detailOldPriceName.setTv(true);
        mBinding.detailOldPriceName.setColor(R.color.color_989898);
        //商品名称
        SpannableString string = new SpannableString("   " + bean.getData().getOtherName());
        CenteredImageSpan imageSpan;
        if (bean.getData().getTag()== 1){
            imageSpan = new CenteredImageSpan(this,R.mipmap.detail_jd);
        }else if (bean.getData().getTag()== 4){
            imageSpan = new CenteredImageSpan(this,R.mipmap.detail_pdd);
        }else if (bean.getData().getTag()== 3){
            imageSpan = new CenteredImageSpan(this,R.mipmap.detail_tm);
        }else {
            imageSpan = new CenteredImageSpan(this,R.mipmap.detail_tb);
        }
        string.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBinding.detailName.setText(string);
        //轮播图
        mBannerList.addAll(bean.getData().getImgList());
        mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
        //商品详情
        goodsType = bean.getData().getGoodsType();
        mBinding.detailDec.setVisibility(View.GONE);
        mBinding.detailDecSpeach.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.down_anim);
        mBinding.detailDown.startAnimation(animation);
        animation.start();
        mDetailList.clear();
        if (bean.getData().getDescImgList() != null && bean.getData().getDescImgList().size() != 0){
            mDetailList.addAll(bean.getData().getDescImgList());
            mImageAdapter.notifyDataSetChanged();
        }
        //推荐理由
        if (!TextUtils.isEmpty(bean.getData().getRecommendReason())){
            mBinding.detailReason.setDesc(bean.getData().getRecommendReason());
        }else {
            mBinding.detailReasonContainer.setVisibility(View.GONE);
        }
        //商家评分
        if (bean.getData().getEvaluateList() == null || bean.getData().getEvaluateList().size() == 0){
            mBinding.detailShopName.setGravity(Gravity.CENTER_VERTICAL);
        }
        if (bean.getData().getEvaluateList() != null && bean.getData().getEvaluateList().size() >= 1){
            mBinding.detailShopCode1Title.setText(bean.getData().getEvaluateList().get(0).getTitle());
            mBinding.detailShopCode1.setText(bean.getData().getEvaluateList().get(0).getScore());
        }
        if (bean.getData().getEvaluateList() != null && bean.getData().getEvaluateList().size() >= 2){
            mBinding.detailShopCode2Title.setText(bean.getData().getEvaluateList().get(1).getTitle());
            mBinding.detailShopCode2.setText(bean.getData().getEvaluateList().get(1).getScore());
        }
        if (bean.getData().getEvaluateList() != null && bean.getData().getEvaluateList().size() >= 3){
            mBinding.detailShopCode3Title.setText(bean.getData().getEvaluateList().get(2).getTitle());
            mBinding.detailShopCode3.setText(bean.getData().getEvaluateList().get(2).getScore());
        }
        //优惠券
        double coupon = new BigDecimal(bean.getData().getCouponPrice()).doubleValue();
        if (coupon == 0){//没有优惠券
            mBinding.detailCouponContainer.setVisibility(View.GONE);
        }else {
            mBinding.detailCouponContainer.setVisibility(View.VISIBLE);
        }
        double free = new BigDecimal(bean.getData().getFee()).doubleValue();
        double price = free + coupon;
        if (price == 0){
            money = "0.00";
        }else {
            money = new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        }
        //是否收藏过
        if(bean.getData().getCollect() == 1){
            isCollect = true;
            Drawable drawable= getResources().getDrawable(R.mipmap.com_favored);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_FF0000));
            mBinding.titleFavor.setCompoundDrawables(null,drawable,null,null);
            mBinding.titleFavor.setText("已收藏");
        }else {
            isCollect = false;
            Drawable drawable= getResources().getDrawable(R.mipmap.com_favor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_565252));
            mBinding.titleFavor.setCompoundDrawables(null,drawable,null,null);
            mBinding.titleFavor.setText("收藏");
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        //会员信息
        level = bean.getData().getLevel() + "";
        UpFee = bean.getData().getUpFee() + "";
        fee = bean.getData().getFee() + "";
        if (bean.getData().getLevel() != 0){//会员
            mBinding.detailMemberText.setText("尊享会员专属优惠，比普通用户多返");
            mBinding.detailMemberPrice.setText("￥" + bean.getData().getUpFee());
            mBinding.detailMemberGo.setVisibility(View.GONE);
            mBinding.shopFee.setText("会员返 ¥" + fee);
            isStart = false;
            mBinding.detailFreeCode.setText("返:"+ new BigDecimal(bean.getData().getBuyPrice()).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            mBinding.detailFreeCodeText.setText("直接买");
            mBinding.detailInvationContainer.setVisibility(View.GONE);
            mBinding.detailImgNotice.setVisibility(View.GONE);
        }else {//非会员
            isStart = true;
            mBinding.detailMemberText.setText("加入极品会员，本商品可返");
            mBinding.detailMemberPrice.setText("￥" + bean.getData().getUpFee());
            mBinding.detailBottomMemberPrice.setText("￥" + bean.getData().getUpFee());
            mBinding.detailMemberGo.setImageResource(R.mipmap.detail_opening);
            mBinding.detailMemberGo.setVisibility(View.VISIBLE);
            mBinding.shopFee.setText("返 ¥" + fee);
            mBinding.detailFreeCode.setText("返:"+ new BigDecimal(bean.getData().getBuyPrice()).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            mBinding.detailFreeCodeText.setText("直接买");
            mBinding.detailInvationContainer.setVisibility(View.GONE);
            mBinding.detailImgNotice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onCollect(TBShoppingDetailBean bean) {
        //优惠券
        double coupon = new BigDecimal(bean.getData().getCouponPrice()).doubleValue();
        if (coupon == 0){//没有优惠券
            mBinding.detailCouponContainer.setVisibility(View.GONE);
        }else {
            mBinding.detailCouponContainer.setVisibility(View.VISIBLE);
        }
        double free = new BigDecimal(bean.getData().getFee()).doubleValue();
        double price = free + coupon;
        if (price == 0){
            money = "0.00";
        }else {
            money = new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        }
        //是否收藏过
        if(bean.getData().getCollect() == 1){
            isCollect = true;
            Drawable drawable= getResources().getDrawable(R.mipmap.com_favored);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_FF0000));
            mBinding.titleFavor.setCompoundDrawables(null,drawable,null,null);
            mBinding.titleFavor.setText("已收藏");
        }else {
            isCollect = false;
            Drawable drawable= getResources().getDrawable(R.mipmap.com_favor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_565252));
            mBinding.titleFavor.setCompoundDrawables(null,drawable,null,null);
            mBinding.titleFavor.setText("收藏");
        }
        //会员信息
        level = bean.getData().getLevel() + "";
        UpFee = bean.getData().getUpFee() + "";
        fee = bean.getData().getFee() + "";
        if (bean.getData().getLevel() != 0){//会员
            mBinding.detailMemberText.setText("尊享会员专属优惠，比普通用户多返");
            mBinding.detailMemberPrice.setText("￥" + bean.getData().getUpFee());
            mBinding.detailMemberGo.setVisibility(View.GONE);
            mBinding.shopFee.setText("会员返 ¥" + fee);
            isStart = false;
            mBinding.detailFreeCode.setText("¥"+ new BigDecimal(free).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            mBinding.detailFreeCodeText.setText("购买返");
            mBinding.detailInvationContainer.setVisibility(View.GONE);
            mBinding.detailImgNotice.setVisibility(View.GONE);
        }else {//非会员
            isStart = true;
            mBinding.detailMemberText.setText("加入极品会员，本商品可返");
            mBinding.detailMemberPrice.setText("￥" + bean.getData().getUpFee());
            mBinding.detailBottomMemberPrice.setText("￥" + bean.getData().getUpFee());
            mBinding.detailMemberGo.setImageResource(R.mipmap.detail_opening);
            mBinding.detailMemberGo.setVisibility(View.VISIBLE);
            mBinding.shopFee.setText("返 ¥" + fee);
            mBinding.detailFreeCode.setText("¥"+ new BigDecimal(bean.getData().getUpFee()).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            mBinding.detailFreeCodeText.setText("购买返");
            mBinding.detailInvationContainer.setVisibility(View.GONE);
            mBinding.detailImgNotice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void LikeSuccess(SimilerGoodsBean bean) {
        mUserLikeList.clear();
        mUserLikeList.addAll(bean.getData());
        mLikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void recommend(SimilerGoodsBean bean) {
        mRecommendList.clear();
        mRecommendList.addAll(bean.getData());
        mRecommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void onComment(CommenBean bean) {
        if (bean.getData() == null){
            mBinding.detailCommentLayout.setVisibility(View.GONE);
            mBinding.detailCommentNum.setText("宝贝评价(0)");
        }else {
            mBinding.detailCommentLayout.setVisibility(View.VISIBLE);
            mBinding.setCommen(bean);
            mBinding.executePendingBindings();
        }
        commenUrl = bean.getUrl();
    }

    @Override
    public void onDescImgs(SucBean<String> bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (bean.getData() != null && bean.getData().size() != 0){
            mBinding.detailDec.setVisibility(View.VISIBLE);
            mBinding.detailDecSpeach.setVisibility(View.GONE);
            mDetailList.clear();
            mDetailList.addAll(bean.getData());
            mImageAdapter.notifyDataSetChanged();
        }else {
            ToastUtil.show("暂无商品详情");
        }
    }

    @Override
    public void onBuyLinkSuccess(ClickUrlBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.detailInvationContainer.setVisibility(View.VISIBLE);
        TaoBaoUtil.openAliHomeWeb(this,bean.getData().getMobileUrl(),bean.getData().getOtherGoodsId());
    }

    @Override
    public void onSucCollectInsert() {
        EventBus.getDefault().post(FovalGoodsFragment.CollectResher);//刷新我的收藏列表
        isCollect = true;
        Drawable drawable= getResources().getDrawable(R.mipmap.com_favored);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_FF0000));
        mBinding.titleFavor.setCompoundDrawables(null,drawable,null,null);
        mBinding.titleFavor.setText("已收藏");
    }

    @Override
    public void onSucCollectDelete() {
        EventBus.getDefault().post(FovalGoodsFragment.CollectResher);//刷新我的收藏列表
        isCollect = false;
        Drawable drawable= getResources().getDrawable(R.mipmap.com_favor);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_565252));
        mBinding.titleFavor.setCompoundDrawables(null,drawable,null,null);
        mBinding.titleFavor.setText("收藏");
    }

    @Override
    public void onShop(ImageBean bean) {
        if (TextUtils.isEmpty(bean.getData())){
            if (mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
            }
            ToastUtil.show("获取店铺地址失败");
            return;
        }
        TaoBaoUtil.openAliHomeWeb(this,bean.getData(),"");
    }

    //获取拼团状态
    public void lookTean(){
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.getReturnGoodsInfo(goodsId,this.bindToLifecycle());
    }

    //弹窗信息
    @Override
    public void onPop(PopBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (bean.getData() != null){
            //有弹窗
            DialogUtil.buyNoticeDialog(this, bean.getData().getAddAllowancePrice(),
                    bean.getAddPoint(), v -> {
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                        mDialog.show();
                        mPresenter.initShare(this.bindToLifecycle());
                    });
        }else {
            DialogUtil.listingDetele(this, "订单消息", "很抱歉未找到您的订单，您可以选择重试\n或在百万补贴页面可查看返利金额",
                    "重试", "关闭", R.color.color_202020, R.color.color_4A90E2, R.color.color_9D9D9D, R.color.color_565252,
                    true, false, v13 -> {
                        //重试
                        lookTean();
                    });
        }
    }

    @Override
    public void initShare(ShareInfoBean bean) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN, mDialog)
                .shareWeb(this, bean.getData().getLink(),bean.getData().getTitle(),bean.getData().getDesc(),
                        bean.getData().getImgUrl(),R.mipmap.share_logo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.detail_decSpeach:
                if (goodsType == 1){
                    if (mDetailList.size() != 0){
                        mBinding.detailDecSpeach.setVisibility(View.GONE);
                        mBinding.detailDec.setVisibility(View.VISIBLE);
                    }else {
                        ToastUtil.show("暂无商品详情");
                    }
                }else {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    mDialog.show();
                    mPresenter.getGoodsDescImgs(goodsId,source,this.bindToLifecycle());
                }
                break;
            case R.id.detail_couponImg:
            case R.id.detail_buy:
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivityForResult(new Intent(this, LoginActivity.class),201);
                    return;
                }
                TaoBaoUtil.openTB(this, () -> {
                    if (level.equals("0")) {
                        //非会员弹窗
                        DialogUtil.buyMemberDialog(this, fee, UpFee, v14 -> {
                            mPresenter.getReturnGoodsClickUrl(goodsId, this.bindToLifecycle());
                        }, v1 -> {
                            buyRefresh = true;
                            startActivity(new Intent(this, HomeNewActivity.class)
                                    .putExtra("type", HomeNewActivity.member)
                            );
                        });
                    } else {
                        mDialog = (new ProgressDialogView()).createPlatformDialog(this, money, R.mipmap.dialog_tb);
                        mDialog.show();
                        mPresenter.getReturnGoodsClickUrl(goodsId, this.bindToLifecycle());
                    }
                });
                break;
            case R.id.detail_home:
                EventBus.getDefault().post(new TBShopDetailBus(TBShopDetailBus.finish));
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                break;
            case R.id.title_favor:
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivityForResult(new Intent(this, LoginActivity.class),201);
                    return;
                }
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                }else{
                    if(isCollect){
                        //收藏过了
                        mPresenter.collectDelete(goodsId,this.bindToLifecycle());
                    }else {
                        //没有收藏
                        mPresenter.collectInsert(goodsId,source,this.bindToLifecycle());
                    }
                }
                break;
            case R.id.detail_bottomMemberGo:
            case R.id.detail_memberGo:
                if (level.equals("0")){
                    buyRefresh = true;
                    startActivity(new Intent(this, HomeNewActivity.class)
                            .putExtra("type",HomeNewActivity.member)
                    );
                }
                break;
            case R.id.detail_memberClose:
                isStart = false;
                mBinding.detailMemberContainer.setVisibility(View.GONE);
                break;
            case R.id.detail_invationContainer:
                //查看买多少返多少弹窗
                lookTean();
                break;
            case R.id.detail_goShop:
                //进入商店
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.getShopUrl(goodsId,this.bindToLifecycle());
                break;
            case R.id.detail_commentGo:
                //查看全部评论
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                TaoBaoUtil.openAliHomeWeb(this,commenUrl,"");
                break;
        }
    }

    @Override
    public void onItemShare(int position) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivityForResult(new Intent(this, LoginActivity.class),201);
            return;
        }
        TaoBaoUtil.openTB(this, () -> {
            startActivity(new Intent(this, ShareActivity.class)
                    .putExtra("otherGoodsId",mUserLikeList.get(position).getOtherGoodsId())
                    .putExtra("source","2")
            );
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
    }

    @Subscribe
    public void finishPage(TBShopDetailBus bus){
        if (bus != null && bus.getTag().equals(TBShopDetailBus.finish)){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == 200){
            //登陆后刷新收藏
            mPresenter.tbGoodsDetail(2,goodsId,source,this.bindToLifecycle());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (buyRefresh){
            buyRefresh = false;
            mPresenter.tbGoodsDetail(2,goodsId,source,this.bindToLifecycle());
        }
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
