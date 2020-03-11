package com.example.administrator.jipinshop.activity.newpeople.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
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
import com.example.administrator.jipinshop.databinding.ActivityNewpeopleDetailBinding;
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
import com.example.administrator.jipinshop.view.textview.CenteredImageSpan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe 新人专区详情
 */
public class NewPeopleDetailActivity extends BaseActivity implements View.OnClickListener, ShoppingQualityAdapter.OnItem, ShoppingParameterAdapter.OnItem, ShoppingUserLikeAdapter.OnItem, NewPeopleDetailView, ShareBoardDialog.onShareListener {

    @Inject
    NewPeopleDetailPresenter mPresenter;

    private ActivityNewpeopleDetailBinding mBinding;
    private String freeId = "";//商品id
    private String otherGoodsId = "";
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
    private int sharePosition = 0;//分享的位置
    private String shareContent = "";
    private String shareUrl = "";
    private int goodsType = 2;//1是极品城  2是淘宝

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_newpeople_detail);
        mBinding.setListener(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        freeId = getIntent().getStringExtra("freeId");
        otherGoodsId = getIntent().getStringExtra("otherGoodsId");
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
        mPresenter.newGoodsDetail(freeId,this.bindToLifecycle());
        Map<String,String> map =  DeviceUuidFactory.getIdfa(this);
        mPresenter.listSimilerGoods(map,otherGoodsId,this.bindToLifecycle());
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
                    mPresenter.getGoodsDescImgs(otherGoodsId,this.bindToLifecycle());
                }
                break;
            case R.id.detail_coupon:
            case R.id.detail_bottom:
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
                    mPresenter.apply(freeId,this.bindToLifecycle());
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

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        String id = "";
        String[] itemShareImage = {""};
        String[] itemShareName = {""};
        id = mUserLikeList.get(sharePosition).getOtherGoodsId();
        itemShareImage[0] = mUserLikeList.get(sharePosition).getImg();
        itemShareName[0] =  mUserLikeList.get(sharePosition).getOtherName();
        String path = "pages/list/main-v2-info/main?id=" + id;
        shareUrl = RetrofitModule.H5_URL + "share/tbGoodsDetail.html?id=" + id;
        if (share_media.equals(SHARE_MEDIA.WEIXIN)){
            if(mDialog != null && !mDialog.isShowing()){
                mDialog.show();
            }
            mPresenter.getTbkGoodsPoster(id , path, itemShareName[0] , itemShareImage[0],this.bindToLifecycle());
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
    public void onSuccess(TBShoppingDetailBean bean) {
        mBinding.setDate(bean.getData());
        mBinding.executePendingBindings();
        mBinding.detailOldPriceName.setTv(true);
        mBinding.detailOldPriceName.setColor(R.color.color_9D9D9D);
        //商品名称
        SpannableString string = new SpannableString("   " + bean.getAllowanceGoods().getGoodsName());
        CenteredImageSpan imageSpan = new CenteredImageSpan(this,R.mipmap.bg_newpeople_tag);
        string.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBinding.detailName.setText(string);
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
        double useAllowancePrice = new BigDecimal(bean.getAllowanceGoods().getUseAllowancePrice()).doubleValue();
        if (coupon != 0 && useAllowancePrice != 0){
            mBinding.detailCouponText.setText("领"+bean.getData().getCouponPrice()+"元优惠券，津贴"+bean.getAllowanceGoods().getUseAllowancePrice()+"元");
        }else if (coupon != 0){
            mBinding.detailCouponText.setText("领"+bean.getData().getCouponPrice()+"元优惠券");
        }else {
            mBinding.detailCouponText.setText("领津贴"+bean.getAllowanceGoods().getUseAllowancePrice()+"元");
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
    public void onBuySuccess(ImageBean bean) {
        TaoBaoUtil.openAliHomeWeb(this, bean.getData(), bean.getOtherGoodsId());
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
    public void onShareSuc(ImageBean bean, String path, String shareName) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN,mDialog)
                .shareWXMin1(this,shareUrl,bean.getData(),shareName,shareContent,path);
    }

    @Override
    public void onShareFile(String path, String shareName, String shareImage) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN,mDialog)
                .shareWXMin1(this,shareUrl,shareImage,shareName,shareContent,path);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
    }
}
