package com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingImageAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingParameterAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingQualityAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingUserLikeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.TBShopDetailBus;
import com.example.administrator.jipinshop.databinding.ActivityTbShopDetailBinding;
import com.example.administrator.jipinshop.fragment.foval.goods.FovalGoodsFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogParameter;
import com.example.administrator.jipinshop.view.dialog.DialogQuality;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * @create 2019/11/25
 * @Describe 淘宝商品详情
 */
public class TBShoppingDetailActivity extends BaseActivity implements View.OnClickListener, ShoppingQualityAdapter.OnItem, ShoppingParameterAdapter.OnItem, TBShoppingDetailView, ShareBoardDialog.onShareListener, ShoppingUserLikeAdapter.OnItem {

    @Inject
    TBShoppingDetailPresenter mPresenter;
    private ActivityTbShopDetailBinding mBinding;
    private String goodsId = "";//商品id
    private Dialog mDialog;
    private Dialog mProgressDialog;
    //banner
    private NoPageBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    //功能评分
    private List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean> mQualityList;
    private ShoppingQualityAdapter mQualityAdapter;
    private DialogQuality mDialogQuality;
    //产品参数
    private List<TBShoppingDetailBean.DataBean.ParametersListBean> mParameterList;
    private ShoppingParameterAdapter mParameterAdapter;
    private DialogParameter mDialogParameter;
    //商品详情
    private List<String> mDetailList;
    private ShoppingImageAdapter mImageAdapter;
    //猜你喜欢
    private List<SimilerGoodsBean.DataBean> mUserLikeList;
    private ShoppingUserLikeAdapter mLikeAdapter;
    //分享面板
    private ShareBoardDialog mShareBoardDialog;
    private int sharePosition = -1;//分享的位置  默认-1是商品本身
    private String shareImage = "";
    private String shareName = "";
    private String shareContent = "";
    private String shareUrl = "";
    private String goodsBuyLink = "";
    private boolean isCollect = false;//标志：是否收藏过此商品 false:没有
    private int goodsType = 2;//1是极品城  2是淘宝

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_tb_shop_detail);
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
        goodsId = getIntent().getStringExtra("otherGoodsId");//商品id
        mPresenter.setStatusBarHight(mBinding.statusBar,this);

        //banner
        mBannerAdapter = new NoPageBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBannerAdapter.setImgCenter(true);
        mBinding.viewPager.setAdapter(mBannerAdapter);

        //功能评分
        mBinding.detailQuality.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mQualityList = new ArrayList<>();
        mQualityAdapter = new ShoppingQualityAdapter(mQualityList,this);
        mQualityAdapter.setOnItem(this);
        mBinding.detailQuality.setAdapter(mQualityAdapter);

        //产品参数
        mBinding.detailParameter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mParameterList = new ArrayList<>();
        mParameterAdapter = new ShoppingParameterAdapter(mParameterList,this);
        mParameterAdapter.setOnItem(this);
        mBinding.detailParameter.setAdapter(mParameterAdapter);

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
        mPresenter.tbGoodsDetail(goodsId,this.bindToLifecycle());
        Map<String,String> map =  DeviceUuidFactory.getIdfa(this);
        mPresenter.listSimilerGoods(map,goodsId,this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.detail_decSpeach:
                if (goodsType == 1){
                    mBinding.detailDecSpeach.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBinding.detailDec.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mBinding.detailDec.setLayoutParams(layoutParams);
                }else {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    mDialog.show();
                    mPresenter.getGoodsDescImgs(goodsId,this.bindToLifecycle());
                }
                break;
            case R.id.detail_couponImg:
            case R.id.detail_buy:
                if (TextUtils.isEmpty(goodsBuyLink)){
                    ToastUtil.show("跳转错误，请退出页面重新进入");
                    return;
                }
                String specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId,"");
                if (TextUtils.isEmpty(specialId) || specialId.equals("null")){
                    TaoBaoUtil.aliLogin(topAuthCode -> {
                        startActivity(new Intent(this, TaoBaoWebActivity.class)
                                .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state="+SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token)+"&view=wap")
                                .putExtra(TaoBaoWebActivity.title,"淘宝授权")
                        );
                    });
                }else {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    mDialog.show();
                    mPresenter.getGoodsClickUrl(goodsBuyLink,goodsId,this.bindToLifecycle());
                }
                break;
            case R.id.detail_share:
                sharePosition = -1;
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog.getInstance("","");
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.detail_home:
                EventBus.getDefault().post(new TBShopDetailBus(TBShopDetailBus.finish));
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                break;
            case R.id.detail_freeNotice:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"fee-rule.html")
                        .putExtra(WebActivity.title,"极品城购物返现说明")
                );
                break;
            case R.id.title_favor:
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
        }
    }

    //功能评分
    @Override
    public void onItemQuality() {
        String json = new Gson().toJson(mQualityList,new TypeToken<List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean>>(){}.getType());
        if (mDialogQuality == null) {
            mDialogQuality = DialogQuality.getInstance(json);
        }
        if (!mDialogQuality.isAdded()) {
            mDialogQuality.show(getSupportFragmentManager(), "mDialogQuality");
        }
    }

    //产品参数
    @Override
    public void onItemParameter() {
        String json = new Gson().toJson(mParameterList,new TypeToken<List<TBShoppingDetailBean.DataBean.ParametersListBean>>(){}.getType());
        if (mDialogParameter == null) {
            mDialogParameter = DialogParameter.getInstance(json);
        }
        if (!mDialogParameter.isAdded()) {
            mDialogParameter.show(getSupportFragmentManager(), "mDialogParameter");
        }
    }

    @Override
    public void onSuccess(TBShoppingDetailBean bean) {
        mBinding.setDate(bean.getData());
        mBinding.executePendingBindings();
        mBinding.detailOldPriceName.setTv(true);
        mBinding.detailOldPriceName.setColor(R.color.color_9D9D9D);
        goodsBuyLink = bean.getData().getGoodsBuyLink();
        //轮播图
        mBannerList.addAll(bean.getData().getImgList());
        mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
        //功能评分
        if (bean.getData().getScoreOptionsList() != null && bean.getData().getScoreOptionsList().size() != 0){
            TBShoppingDetailBean.DataBean.ScoreOptionsListBean scoreOptionsListBean = new TBShoppingDetailBean.DataBean.ScoreOptionsListBean();
            scoreOptionsListBean.setName("综合评分");
            scoreOptionsListBean.setScore(bean.getData().getScore());
            mQualityList.add(scoreOptionsListBean);
            mQualityList.addAll(bean.getData().getScoreOptionsList());
            mQualityAdapter.notifyDataSetChanged();
        }else {
            mBinding.detailQualityContainer.setVisibility(View.GONE);
        }
        //产品参数
        if (bean.getData().getParametersList() != null && bean.getData().getParametersList().size() !=0){
            mParameterList.addAll(bean.getData().getParametersList());
            mParameterAdapter.notifyDataSetChanged();
        }else {
            mBinding.detailParameterContainer.setVisibility(View.GONE);
        }
        if (mBinding.detailQualityContainer.getVisibility() == View.GONE &&
                mBinding.detailParameterContainer.getVisibility() == View.GONE){
            mBinding.detailLine2.setVisibility(View.GONE);
        }
        //商品详情
        goodsType = bean.getData().getGoodsType();
        if (goodsType == 2){
            mBinding.detailDec.setVisibility(View.GONE);
        }else {//极品城上架商品和原来一样
            if (bean.getData().getDescImgList() != null && bean.getData().getDescImgList().size() != 0){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBinding.detailDec.getLayoutParams();
                layoutParams.height = (int) getResources().getDimension(R.dimen.y600);
                mBinding.detailDec.setLayoutParams(layoutParams);
                mDetailList.addAll(bean.getData().getDescImgList());
                mImageAdapter.notifyDataSetChanged();
            }else {
                mBinding.detailDecContainer.setVisibility(View.GONE);
            }
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
        if (free == 0){//没有补贴
            mBinding.detailFeeContainer.setVisibility(View.GONE);
            mBinding.detailFreeNotice.setVisibility(View.GONE);
        }
        double price = free + coupon;
        if (price == 0){
            mBinding.detailFreeCode.setVisibility(View.GONE);
        }else {
            mBinding.detailFreeCode.setText("（省¥"+ new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString() +"）");
        }
        //分享
        shareImage = bean.getData().getImg();
        shareName = bean.getData().getOtherName();
        shareContent = "【分享来自极品城APP】看评测选好物，省心更省钱";
        //是否收藏过
        if(bean.getData().getCollect() == 1){
            isCollect = true;
            mBinding.titleFavorImg.setImageResource(R.mipmap.com_favored);
            mBinding.titleFavorImg.setColorFilter(getResources().getColor(R.color.color_E25838));
        }else {
            isCollect = false;
            mBinding.titleFavorImg.setImageResource(R.mipmap.com_favor);
            mBinding.titleFavorImg.setColorFilter(Color.WHITE);
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
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
    public void LikeSuccess(SimilerGoodsBean bean) {
        mUserLikeList.clear();
        mUserLikeList.addAll(bean.getData());
        mLikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSucCollectInsert() {
        EventBus.getDefault().post(FovalGoodsFragment.CollectResher);//刷新我的收藏列表
        isCollect = true;
        mBinding.titleFavorImg.setImageResource(R.mipmap.com_favored);
        mBinding.titleFavorImg.setColorFilter(getResources().getColor(R.color.color_E25838));
    }

    @Override
    public void onSucCollectDelete() {
        EventBus.getDefault().post(FovalGoodsFragment.CollectResher);//刷新我的收藏列表
        isCollect = false;
        mBinding.titleFavorImg.setImageResource(R.mipmap.com_favor);
        mBinding.titleFavorImg.setColorFilter(Color.WHITE);
    }

    @Override
    public void onBuyLinkSuccess(ImageBean bean) {
        TaoBaoUtil.openAliHomeWeb(this,bean.getData(),"");
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
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        String id = "";
        String[] itemShareImage = {""};
        String[] itemShareName = {""};
        if (sharePosition == -1){
            id = goodsId;
            itemShareImage[0] = shareImage;
            itemShareName[0] = shareName;
        }else {
            id = mUserLikeList.get(sharePosition).getOtherGoodsId();
            itemShareImage[0] = mUserLikeList.get(sharePosition).getImg();
            itemShareName[0] =  mUserLikeList.get(sharePosition).getOtherName();
        }
        String path = "pages/list/main-v2-info/main?id=" + id;
        shareUrl = RetrofitModule.H5_URL + "share/tbGoodsDetail.html?id=" + id;
        if (share_media.equals(SHARE_MEDIA.WEIXIN)){
            new ShareUtils(this, share_media,mDialog)
                    .shareWXMin1(this,shareUrl,itemShareImage[0],itemShareName[0],shareContent,path);
        }else {
            MobLinkUtil.mobShare(id, "/tbkGoodsDetail", mobID -> {
                if (!TextUtils.isEmpty(mobID)){
                    shareUrl += "&mobid=" + mobID;
                }
                new ShareUtils(this, share_media,mDialog)
                        .shareWeb(this, shareUrl, itemShareName[0], shareContent, itemShareImage[0], R.mipmap.share_logo);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void finishPage(TBShopDetailBus bus){
        if (bus != null && bus.getTag().equals(TBShopDetailBus.finish)){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
    }

    @Override
    public void onItemShare(int position) {
        sharePosition = position;
        if (mShareBoardDialog == null) {
            mShareBoardDialog = ShareBoardDialog.getInstance("","");
            mShareBoardDialog.setOnShareListener(this);
        }
        if (!mShareBoardDialog.isAdded()) {
            mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
        }
    }
}
