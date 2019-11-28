package com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingImageAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingParameterAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingQualityAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingUserLikeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityTbShopDetailBinding;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

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
public class TBShoppingDetailActivity extends BaseActivity implements View.OnClickListener, ShoppingQualityAdapter.OnItem, ShoppingParameterAdapter.OnItem, TBShoppingDetailView {

    @Inject
    TBShoppingDetailPresenter mPresenter;
    private ActivityTbShopDetailBinding mBinding;
    private String goodsId = "";//商品id
    private Dialog mDialog;
    //banner
    private NoPageBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    //功能评分
    private List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean> mQualityList;
    private ShoppingQualityAdapter mQualityAdapter;
    //产品参数
    private List<TBShoppingDetailBean.DataBean.ParametersListBean> mParameterList;
    private ShoppingParameterAdapter mParameterAdapter;
    //商品详情
    private List<String> mDetailList;
    private ShoppingImageAdapter mImageAdapter;
    //猜你喜欢
    private List<SimilerGoodsBean.DataBean> mUserLikeList;
    private ShoppingUserLikeAdapter mLikeAdapter;

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
        initView();
    }

    private void initView() {
        goodsId = getIntent().getStringExtra("otherGoodsId");//商品id
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
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
        mBinding.detailUserLike.setAdapter(mLikeAdapter);

        //模拟数据
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
                mBinding.detailDecSpeach.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.detailDec.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mBinding.detailDec.setLayoutParams(layoutParams);
                break;
            case R.id.detail_couponImg:
            case R.id.detail_buy:
                ToastUtil.show("购买");
                break;
            case R.id.detail_share:
                ToastUtil.show("分享");
                break;
            case R.id.detail_home:
                ToastUtil.show("首页");
                break;
            case R.id.detail_freeNotice:
                ToastUtil.show("极品城补贴规则");
                break;
            case R.id.title_favor:
                ToastUtil.show("收藏");
                break;
        }
    }

    //功能评分
    @Override
    public void onItemQuality() {
        ToastUtil.show("功能评分");
    }

    //产品参数
    @Override
    public void onItemParameter() {
        ToastUtil.show("产品参数");
    }

    @Override
    public void onSuccess(TBShoppingDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.setDate(bean.getData());
        mBinding.executePendingBindings();
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
        //商品详情
        if (bean.getData().getDescImgList() != null && bean.getData().getDescImgList().size() != 0){
            mDetailList.addAll(bean.getData().getDescImgList());
            mImageAdapter.notifyDataSetChanged();
        }else {
            mBinding.detailDecContainer.setVisibility(View.GONE);
        }
        //推荐理由
        if (!TextUtils.isEmpty(bean.getData().getRecommendReason())){
            mBinding.detailReason.setDesc(bean.getData().getRecommendReason());
        }else {
            mBinding.detailReasonContainer.setVisibility(View.GONE);
        }
        //商家评分
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
            mBinding.detailFreeCode.setText("（省¥"+ price +"）");
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void LikeSuccess(SimilerGoodsBean bean) {
        mUserLikeList.clear();
        mUserLikeList.addAll(bean.getData());
        mLikeAdapter.notifyDataSetChanged();
    }
}
