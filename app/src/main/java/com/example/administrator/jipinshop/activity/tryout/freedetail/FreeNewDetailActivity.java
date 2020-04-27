package com.example.administrator.jipinshop.activity.tryout.freedetail;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.databinding.ActivityFreenewDetailBinding;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopDescriptionFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopRuleFragment2;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopUserFragment;
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
 * @create 2019/11/11
 * @Describe 新免单详情页面
 */
public class FreeNewDetailActivity extends BaseActivity implements View.OnClickListener, FreeNewDetailView, ShareBoardDialog2.onShareListener {

    @Inject
    FreeNewDetailPresenter mPresenter;

    private ActivityFreenewDetailBinding mBinding;
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
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_freenew_detail);
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

        mPresenter.setTitle(mBinding.appbar,mBinding.detailBack,mBinding.statusBar);
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
        mBannerAdapter.setPagerIndex(mBinding.pagerIndex);
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
                if (mBinding.detailInvation.getText().toString().equals("立即购买")){
                    //弹框
                    TaoBaoUtil.openTB(this, () -> {
                        DialogUtil.freeBuyDialog(this, actualPrice, freePrice, v1 -> {
                            //去购买
                            mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                            mDialog.show();
                            mPresenter.freeAppley(freeId,this.bindToLifecycle());
                        });
                    });
                }else {
                    //邀请
                    if (mShareBoardDialog == null) {
                        mShareBoardDialog = ShareBoardDialog2.getInstance();
                        mShareBoardDialog.setOnShareListener(this);
                    }
                    if (!mShareBoardDialog.isAdded()) {
                        mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog2");
                    }
                }
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
        mBinding.pagerIndex.setText("1/"+mBannerList.size());
        mBinding.itemProgressbar.setTotalAndCurrentCount(detailBean.getData().getInviteUserCount(),detailBean.getData().getMyInviteUserCount());
        mBinding.itemProgressbar.post(() -> {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.detailProgress.getLayoutParams();
            if (detailBean.getData().getMyInviteUserCount() < detailBean.getData().getInviteUserCount()){
                int w = (int) mBinding.itemProgressbar.getSideWidth() - (mBinding.detailProgress.getMeasuredWidth() / 2 );
                if (w > 0){
                    layoutParams.leftMargin = w;
                    mBinding.detailProgress.setLayoutParams(layoutParams);
                }
            }else {
                mBinding.detailProgress.setVisibility(View.GONE);
                mBinding.detailProgress2.setImageResource(R.mipmap.free_progrss);
            }

            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mBinding.detailProgressStart.getLayoutParams();
            int wT = (int) (mBinding.itemProgressbar.getSideWidth() - (mBinding.detailProgressStart.getMeasuredWidth() / 2));
            if(wT > 0){
                layoutParams1.leftMargin = wT;
                mBinding.detailProgressStart.setLayoutParams(layoutParams1);
            }
            if (wT >= getResources().getDimension(R.dimen.x450)){
                mBinding.detailProgressStart.setVisibility(View.GONE);
            }else {
                mBinding.detailProgressStart.setVisibility(View.VISIBLE);
            }
        });
        String html = "<b>免单提示：</b>" + detailBean.getData().getFreeNote();
        mBinding.detailRuleText.setText(Html.fromHtml(html));
        if (detailBean.getData().getMyInviteUserCount() >= detailBean.getData().getInviteUserCount()){
            mBinding.detailInvationContainer.setVisibility(View.GONE);
            mBinding.detailInvationText.setVisibility(View.VISIBLE);
            mBinding.detailInvation.setText("立即购买");
        }else {
            mBinding.detailInvationContainer.setVisibility(View.VISIBLE);
            mBinding.detailInvationText.setVisibility(View.GONE);
            mBinding.detailInvation.setText("立即邀请");
        }
        mBinding.detailOldPrice.setColor(R.color.color_white);
        mBinding.detailOldPrice.setTv(true);
        mFragments.clear();
        mFragments.add(ShopDescriptionFragment.getInstance(new Gson().toJson(detailBean.getData().getGoodsContentList(),new TypeToken<List<FreeDetailBean.DataBean.GoodsContentListBean>>(){}.getType())));
        mFragments.add(ShopUserFragment.getInstance(detailBean.getData().getId(),detailBean.getData().getFreePrice()));
        mFragments.add(ShopRuleFragment2.getInstance());
        mHomeAdapter.setFragments(mFragments);
        mBinding.detailViewpager.setAdapter(mHomeAdapter);
        mBinding.detailViewpager.setOffscreenPageLimit(mFragments.size() - 1);
        mBinding.tabLayout.setupWithViewPager(mBinding.detailViewpager);
        mPresenter.initTabLayout(this,mFragments,mBinding.tabLayout);
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
            String path = "pages/main/main-v2-info/index?id=" + freeId + "&fromUserId=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId);
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
}
