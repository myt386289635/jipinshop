package com.example.administrator.jipinshop.activity.newpeople.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeNewDetailView;
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.databinding.ActivityNewpeopleDetailBinding;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopDescriptionFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopUserFragment2;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe 新人专区详情
 */
public class NewPeopleDetailActivity extends BaseActivity implements View.OnClickListener, FreeNewDetailView, ShareBoardDialog2.onShareListener, ViewPager.OnPageChangeListener {

    @Inject
    NewPeopleDetailPresenter mPresenter;

    private ActivityNewpeopleDetailBinding mBinding;
    private String freeId = "";
    private Dialog mDialog;
    //banner
    private NoPageBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    //三个fragment
    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;
    // 解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
    float startX = 0;
    float startY = 0;
    float xDistance = 0;
    float yDistance = 0;
    //分享
    private ShareBoardDialog2 mShareBoardDialog;
    private String shareImage = "";
    private String shareName = "";
    private String shareContent = "";
    private String shareUrl = "";
    private String actualPrice = "";
    private String freePrice = "";
    private String postShare = "";

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

        mPresenter.setTitle(mBinding.appbar,mBinding.statusBar);
        mPresenter.init(this,mBinding.detailPager,mBinding.statusBar,mBinding.statusBar1,mBinding.titleContainer);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();

        //banner
        mBannerAdapter = new NoPageBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.detailPager);
        mBinding.detailPager.setAdapter(mBannerAdapter);
        //初始化
        mFragments = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());

        mPresenter.getDate(freeId,this.bindToLifecycle());
        mPresenter.createFreePoster(freeId,this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
            case R.id.title_back:
                finish();
                break;
            case R.id.title_favor:
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog2");
                }
                break;
            case R.id.detail_invation:
                //弹框
                String specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId,"");
                if (TextUtils.isEmpty(specialId) || specialId.equals("null")){
                    TaoBaoUtil.aliLogin(topAuthCode -> {
                        startActivity(new Intent(this, TaoBaoWebActivity.class)
                                .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state="+SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token)+"&view=wap")
                                .putExtra(TaoBaoWebActivity.title,"淘宝授权")
                        );
                    });
                }else {
                    DialogUtil.NewPeopleBuyDialog(this, actualPrice, freePrice, v1 -> {
                        //去购买
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                        mDialog.show();
                        mPresenter.freeAppley(freeId, this.bindToLifecycle());
                    });
                }
                break;
            case R.id.detail_title1Container:
                //商品详情
                mBinding.detailTitle1.setTextColor(getResources().getColor(R.color.color_050505));
                mBinding.detailTitle2.setTextColor(getResources().getColor(R.color.color_ACACAC));
                mBinding.detailTitle1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                mBinding.detailTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                mBinding.detailLine1.setVisibility(View.VISIBLE);
                mBinding.detailLine2.setVisibility(View.GONE);
                mBinding.detailViewpager.setCurrentItem(0);
                break;
            case R.id.detail_title2Container:
                //参与名单
                mBinding.detailTitle2.setTextColor(getResources().getColor(R.color.color_050505));
                mBinding.detailTitle1.setTextColor(getResources().getColor(R.color.color_ACACAC));
                mBinding.detailTitle1.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                mBinding.detailTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                mBinding.detailLine1.setVisibility(View.GONE);
                mBinding.detailLine2.setVisibility(View.VISIBLE);
                mBinding.detailViewpager.setCurrentItem(1);
                break;
        }
    }

    //解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        final float curX = ev.getX();
        final float curY = ev.getY();

        xDistance += Math.abs(curX - startX);
        yDistance += Math.abs(curY - startY);

        if (xDistance >= yDistance) {
            //横向滑动
            mBinding.detailViewpager.setNoScroll(false);
        } else {
            //垂直滑动
            mBinding.detailViewpager.setNoScroll(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    public AppBarLayout getBar() {
        return mBinding.appbar;
    }

    @Override
    public void onSuccess(FreeDetailBean detailBean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        actualPrice = detailBean.getData().getActualPrice();
        freePrice = detailBean.getData().getFreePrice();
        shareImage = detailBean.getData().getShareImg();
        shareName = detailBean.getData().getShareTitle();
        shareContent = detailBean.getData().getShareContent();
        shareUrl = detailBean.getData().getShareUrl();
        mBinding.setDate(detailBean.getData());
        mBannerList.addAll(detailBean.getData().getImgList());
        mPresenter.initBanner(mBannerList, this, point, mBannerAdapter);
        String html = "<b>免单提示：</b>" + detailBean.getData().getFreeNote();
        mBinding.detailRuleText.setText(Html.fromHtml(html));
        mBinding.detailOldPrice.setColor(R.color.color_white);
        mBinding.detailOldPrice.setTv(true);
        mFragments.clear();
        mFragments.add(ShopDescriptionFragment.getInstance(new Gson().toJson(detailBean.getData().getGoodsContentList(),new TypeToken<List<FreeDetailBean.DataBean.GoodsContentListBean>>(){}.getType())));
        mFragments.add(ShopUserFragment2.getInstance(detailBean.getData().getId(),detailBean.getData().getFreePrice()));
        mHomeAdapter.setFragments(mFragments);
        mBinding.detailViewpager.setAdapter(mHomeAdapter);
        mBinding.detailViewpager.addOnPageChangeListener(this);
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onApply(ImageBean bean) {
        TaoBaoUtil.openAliHomeWeb(this,bean.getData(),bean.getOtherGoodsId());
    }

    @Override
    public void onPoster(ImageBean bean) {
        postShare = bean.getData();
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        if (share_media.equals(SHARE_MEDIA.WEIXIN)){
            //分享小程序
            String path = "pages/main/main-new-info/index?id="+ freeId + "&fromUserId=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId) ;
            new ShareUtils(this, share_media,mDialog)
                    .shareWXMin2(this,shareUrl,shareImage,shareName,shareContent,path);
        }else if (share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
            //分享图片到朋友圈
            new ShareUtils(this, share_media, mDialog)
                    .shareImage(this, postShare);
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
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0){
            mBinding.detailTitle1.setTextColor(getResources().getColor(R.color.color_050505));
            mBinding.detailTitle2.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailTitle1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            mBinding.detailTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            mBinding.detailLine1.setVisibility(View.VISIBLE);
            mBinding.detailLine2.setVisibility(View.GONE);
        }else {
            mBinding.detailTitle2.setTextColor(getResources().getColor(R.color.color_050505));
            mBinding.detailTitle1.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailTitle1.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            mBinding.detailTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            mBinding.detailLine1.setVisibility(View.GONE);
            mBinding.detailLine2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) { }
}
