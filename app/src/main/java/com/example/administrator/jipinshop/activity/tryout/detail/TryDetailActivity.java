package com.example.administrator.jipinshop.activity.tryout.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.tryout.passedMore.PassedMoreActivity;
import com.example.administrator.jipinshop.activity.tryout.reportMore.ReportMoreActivity;
import com.example.administrator.jipinshop.activity.tryout.shareMore.ShareMoreActivity;
import com.example.administrator.jipinshop.adapter.CommenBannerAdapter;
import com.example.administrator.jipinshop.adapter.TryDetailApplyRVAdapter;
import com.example.administrator.jipinshop.adapter.TryDetailGVAdapter;
import com.example.administrator.jipinshop.adapter.TryDetailRVAdapter;
import com.example.administrator.jipinshop.adapter.TryDetailReportRVAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.TryApplyBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.TryShopBus;
import com.example.administrator.jipinshop.bean.eventbus.TryStatusBus;
import com.example.administrator.jipinshop.databinding.ActivityTryDetailBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.trello.rxlifecycle2.android.ActivityEvent;
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
 * @create 2019/3/21
 * @Describe 试用详情页面
 */
public class TryDetailActivity extends BaseActivity implements View.OnClickListener, ShareBoardDialog.onShareListener, TryDetailView, TryDetailReportRVAdapter.OnItemClick {

    @Inject
    TryDetailPresenter mPresenter;

    private ActivityTryDetailBinding mBinding;
    /**
     * 分享面板
     */
    private ShareBoardDialog mShareBoardDialog;
    /**
     * 分享的东西
     */
    private String shareTitle = "";
    private String shareContent  = "";
    private String shareImg  = "";
    private String shareUrl  = "";
    private TryDetailBean mTryDetailBean;//用于分享和拉赞的url以及获取申请条件、获取试用商品id

    private List<TextView> mTabTextView;//记录热卖榜等4个榜的title 这个值是有顺序的，需要和set数组顺序一致
    private List<View> mTabLine;//记录热卖榜等4个榜的line  这个值是有顺序的，需要和set数组顺序一致
    private Dialog mDialog;
    //banner
    private CommenBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 100){
                if (mBinding.viewPager != null) {
                    mBinding.viewPager.setCurrentItem(mBinding.viewPager.getCurrentItem() + 1);
                }
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean stopThread = true;

    private String goodsBuyLink = "";
    private CountDownTimer mCountDownTimerUtils;//定时器

    //商品详情
    private TryDetailRVAdapter mRVAdapter;
    private List<TryDetailBean.DataBean.GoodsContentListBean> mList;

    //试用通过名单
    private TryDetailGVAdapter mGVAdapter;
    private List<TryDetailBean.DataBean.PassedUserListBean> mPassedUserListBeans;
    //分享拉赞列表
    private List<TryDetailBean.DataBean.ApplyUserListBean> mApplyUserListBeans;
    private TryDetailApplyRVAdapter mApplyRVAdapter;
    //试用报告
    private List<TryDetailBean.DataBean.ReportArticleListBean> mReportListBeans;
    private TryDetailReportRVAdapter mReportRVAdapter;

    private Boolean shareFlag = true;//是分享还是拉赞  true分享 false拉赞


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_try_detail);
        mBinding.setListener(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.titleTop, mBinding.statusBar , mBinding.statusBar2 , mBinding.titleContainer,this);
        int dimenTop = (int) getResources().getDimension(R.dimen.x22);
        int dimenLeft = (int) getResources().getDimension(R.dimen.x40);
        mBinding.inClude.titleBack.setPadding(dimenLeft,dimenTop,dimenLeft,dimenTop);
        mBinding.inClude.titleTv.setText("试用商品");
        mTabTextView = new ArrayList<>();
        mTabLine = new ArrayList<>();
        mTabTextView.add(mBinding.detailTab1.tabText);
        mTabTextView.add(mBinding.detailTab2.tabText);
        mTabTextView.add(mBinding.detailTab3.tabText);
        mTabTextView.add(mBinding.detailTab4.tabText);
        mTabLine.add(mBinding.detailTab1.tabLine);
        mTabLine.add(mBinding.detailTab2.tabLine);
        mTabLine.add(mBinding.detailTab3.tabLine);
        mTabLine.add(mBinding.detailTab4.tabLine);
        mPresenter.initTabLayout(this,mBinding);
        mPresenter.initText(this,mBinding);

        mBannerAdapter = new CommenBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBinding.viewPager.setAdapter(mBannerAdapter);
        mBinding.viewPager.setCurrentItem(mBannerList.size() * 10);

        mList = new ArrayList<>();
        mRVAdapter = new TryDetailRVAdapter(this,mList);
        mBinding.detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.detailRecyclerView.setAdapter(mRVAdapter);

        //控制头布局滑动位置的变化
        mBinding.scrollView.setOnScrollListener(scrollY -> {
            if (mBinding.scrollView.getScrollY() >= mBinding.detailLine3.getTop()) {
                mPresenter.seleteTab(TryDetailActivity.this, 3, mTabTextView, mTabLine);
            } else if (mBinding.scrollView.getScrollY() >= mBinding.detailLine2.getTop()) {
                mPresenter.seleteTab(TryDetailActivity.this, 2, mTabTextView, mTabLine);
            } else if (mBinding.scrollView.getScrollY() >= mBinding.detailLine1.getTop()) {
                mPresenter.seleteTab(TryDetailActivity.this, 1, mTabTextView, mTabLine);
            } else {
                mPresenter.seleteTab(TryDetailActivity.this, 0, mTabTextView, mTabLine);
            }
        });

        //申请成功人数列表
        mPassedUserListBeans = new ArrayList<>();
        mGVAdapter = new TryDetailGVAdapter(this,mPassedUserListBeans);
        mBinding.passedUserListGridview.setAdapter(mGVAdapter);
        //分享拉赞列表
        mBinding.userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mApplyUserListBeans = new ArrayList<>();
        mApplyRVAdapter =  new TryDetailApplyRVAdapter(this,mApplyUserListBeans);
        mBinding.userListRecyclerView.setAdapter(mApplyRVAdapter);
        //试用报告
        mBinding.reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReportListBeans = new ArrayList<>();
        mReportRVAdapter = new TryDetailReportRVAdapter(this,mReportListBeans);
        mReportRVAdapter.setOnItemClick(this);
        mBinding.reportRecyclerView.setAdapter(mReportRVAdapter);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
        mDialog.show();
        mPresenter.tryDetail(getIntent().getStringExtra("id"),this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nodate_back:
            case R.id.title_back:
                finish();
                break;
            case R.id.detail_share:
            case R.id.title_favor://分享
                if (mTryDetailBean == null){
                    ToastUtil.show("分享失败");
                    return;
                }
                shareFlag = true;
                shareTitle = mTryDetailBean.getData().getShareTitle();
                shareContent = mTryDetailBean.getData().getShareContent();
                shareImg = mTryDetailBean.getData().getShareImg();
                shareUrl = mTryDetailBean.getData().getShareUrl();
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = new ShareBoardDialog();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.detail_apply://免费申请
                if (mTryDetailBean == null){
                    ToastUtil.show("页面请求错误，请重新打开");
                    return;
                }
                if(mBinding.detailApply.getText().toString().equals("免费申请")){
                    if (mTryDetailBean.getData().getApplyPoint() <= SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0)){
                        DialogUtil.buleDialog(this, "商品试用需要支付" + mTryDetailBean.getData().getApplyPoint() + "极币，是否确认参与？",
                                "确认申请", v1 -> {
                                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
                                    mDialog.show();
                                    mPresenter.tryApply(getIntent().getStringExtra("id"),this.bindToLifecycle());
                                });
                    }else {
                        DialogUtil.SingleDialog(this, "极币数不足，请前往获取极币", "去赚极币", v12 -> {
                            //跳转到签到页面
                            startActivity(new Intent(this, SignActivity.class));
                        });
                    }
                    UAppUtil.goods_trier(this,1);
                }else if(mBinding.detailApply.getText().toString().equals("分享拉赞")){
                    if (mTryDetailBean == null){
                        ToastUtil.show("分享失败");
                        return;
                    }
                    shareFlag = false;
                    shareTitle = mTryDetailBean.getData().getVoteShareTitle();
                    shareContent = mTryDetailBean.getData().getVoteShareContent();
                    shareImg = mTryDetailBean.getData().getVoteShareImg();
                    shareUrl = mTryDetailBean.getData().getVoteShareUrl();
                    if (mShareBoardDialog == null) {
                        mShareBoardDialog = new ShareBoardDialog();
                        mShareBoardDialog.setOnShareListener(this);
                    }
                    if (!mShareBoardDialog.isAdded()) {
                        mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                    }
                }else if(mBinding.detailApply.getText().toString().equals("查看名单")){
                    startActivity(new Intent(this, PassedMoreActivity.class)
                            .putExtra("id",mTryDetailBean.getData().getId())
                    );
                }else if(mBinding.detailApply.getText().toString().equals("查看试用报告")){
                    startActivity(new Intent(this, ReportMoreActivity.class)
                            .putExtra("id",getIntent().getStringExtra("id"))
                    );
                }

                break;
            case R.id.detail_buy: // 优惠购买
                if (TextUtils.isEmpty(goodsBuyLink)){
                    ToastUtil.show("暂无商品链接");
                    return;
                }
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                openAliHomeWeb(goodsBuyLink);
                UAppUtil.goods_trier(this,0);
                break;
            case R.id.detail_comment:
                if (mTryDetailBean == null){
                    ToastUtil.show("页面请求错误，请重新打开");
                    return;
                }
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position",-1)
                        .putExtra("id",mTryDetailBean.getData().getId())
                        .putExtra("type","5")
                );
                break;
            case R.id.detail_tab1:
                mBinding.appbar.setExpanded(true);//展开
                mBinding.scrollView.scrollTo(0, 0);
                mPresenter.seleteTab(this,0,mTabTextView,mTabLine);
                break;
            case R.id.detail_tab2:
                mBinding.appbar.setExpanded(false);//折叠
                mBinding.scrollView.scrollTo(0, mBinding.detailLine1.getTop());
                mPresenter.seleteTab(this,1,mTabTextView,mTabLine);
                break;
            case R.id.detail_tab3:
                mBinding.appbar.setExpanded(false);//折叠
                mBinding.scrollView.scrollTo(0, mBinding.detailLine2.getTop());
                mPresenter.seleteTab(this,2,mTabTextView,mTabLine);
                break;
            case R.id.detail_tab4:
                mBinding.appbar.setExpanded(false);//折叠
                mBinding.scrollView.scrollTo(0, mBinding.detailLine3.getTop());
                mPresenter.seleteTab(this,3,mTabTextView,mTabLine);
                break;
            case R.id.detail_passedMore:
                //查看更多申请成功名单
                if (mTryDetailBean == null){
                    ToastUtil.show("页面请求错误，请重新打开");
                    return;
                }
                startActivity(new Intent(this, PassedMoreActivity.class)
                        .putExtra("id",mTryDetailBean.getData().getId())
                );
                break;
            case R.id.detail_shareMore:
                //查看更多拉赞名单
                if (mTryDetailBean == null){
                    ToastUtil.show("页面请求错误，请重新打开");
                    return;
                }
                startActivity(new Intent(this, ShareMoreActivity.class)
                        .putExtra("id",mTryDetailBean.getData().getId())
                );
                break;
            case R.id.detail_reportMore:
                //查看更多试用报告
                if (mTryDetailBean == null){
                    ToastUtil.show("页面请求错误，请重新打开");
                    return;
                }
                startActivity(new Intent(this, ReportMoreActivity.class)
                        .putExtra("id",getIntent().getStringExtra("id"))
                );
                break;
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        if (shareFlag){
            mPresenter.taskshareFinish(this.bindUntilEvent(ActivityEvent.DESTROY));
        }
        new ShareUtils(this, share_media)
                .shareWeb(this, shareUrl, shareTitle, shareContent, shareImg, R.mipmap.share_logo);
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
        stopThread = false;
        mHandler.removeCallbacksAndMessages(null);
        if(mCountDownTimerUtils != null){
            mCountDownTimerUtils.cancel();
            mCountDownTimerUtils = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess(TryDetailBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mTryDetailBean = bean;
        mBinding.detailNoDate.setVisibility(View.GONE);
        //轮播图
        if(bean.getData().getImgList().size() == 1){
            mBinding.viewPager.setVisibility(View.GONE);
            mBinding.detailPoint.setVisibility(View.GONE);
            mBinding.pagerImage.setVisibility(View.VISIBLE);
            GlideApp.loderImage(this,bean.getData().getImgList().get(0),mBinding.pagerImage,0,0);
        }else {
            mBinding.viewPager.setVisibility(View.VISIBLE);
            mBinding.detailPoint.setVisibility(View.VISIBLE);
            mBinding.pagerImage.setVisibility(View.GONE);
            mBannerList.addAll(bean.getData().getImgList());
            mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
            new Thread(new MyRunble()).start();
        }
        goodsBuyLink = bean.getData().getGoodsBuyLink();
        mBinding.detailName.setText(bean.getData().getName());
        String countHtml =  "试用数量  " + "<font color='#E31436' >"+ bean.getData().getCount() +"</font>";
        mBinding.detailTryNumber.setText(Html.fromHtml(countHtml));
        BigDecimal bigDecimal = new BigDecimal(bean.getData().getActualPrice());
        String priceHtml =  "市场价  " + "<font color='#E31436' >¥"+ bigDecimal.setScale(2,BigDecimal.ROUND_DOWN).toString() +"</font>";
        mBinding.detailPrice.setText(Html.fromHtml(priceHtml));
        String applyCount = "已申请人数  " + "<font color='#E31436' >"+ bean.getData().getApplyUserCount() +"</font>";
        mBinding.detailTryPeople.setText(Html.fromHtml(applyCount));
        mBinding.detailApplyCode.setText(bean.getData().getApplyPoint() + "");

        if (bean.getData().getStatus() == 2){//进行中
            mBinding.detailApplyAble.setVisibility(View.VISIBLE);
            mBinding.detailApplying.setVisibility(View.GONE);
            mBinding.detailTimeDay0.setText(bean.getData().getEndtimeObj().getDay().substring(0,1));
            mBinding.detailTimeDay1.setText(bean.getData().getEndtimeObj().getDay().substring(1,2));
            mBinding.detailTimeHour0.setText(bean.getData().getEndtimeObj().getHours().substring(0,1));
            mBinding.detailTimeHour1.setText(bean.getData().getEndtimeObj().getHours().substring(1,2));
            mBinding.detailTimeMin0.setText(bean.getData().getEndtimeObj().getMinutes().substring(0,1));
            mBinding.detailTimeMin1.setText(bean.getData().getEndtimeObj().getMinutes().substring(1,2));
            mBinding.detailTimeSecond0.setText(bean.getData().getEndtimeObj().getSeconds().substring(0,1));
            mBinding.detailTimeSecond1.setText(bean.getData().getEndtimeObj().getSeconds().substring(1,2));
            BigDecimal bigDay = new BigDecimal(bean.getData().getEndtimeObj().getDay());
            BigDecimal bigHour = new BigDecimal(bean.getData().getEndtimeObj().getHours());
            BigDecimal bigMin = new BigDecimal(bean.getData().getEndtimeObj().getMinutes());
            BigDecimal bigSecond = new BigDecimal(bean.getData().getEndtimeObj().getSeconds());
            if(bean.getData().getApplied() == 1){
                //该人已申请
                mBinding.detailApply.setText("分享拉赞");
            }else {
                //未申请
                mBinding.detailApply.setText("免费申请");
            }
            setCountDownTimer(bigDay.intValue(),bigHour.intValue(),bigMin.intValue(),bigSecond.intValue(),bean.getData().getApplied());
        }else if (bean.getData().getStatus() == 3){
            //试用中
            mBinding.detailApply.setText("查看名单");
            mBinding.detailApplyAble.setVisibility(View.GONE);
            mBinding.detailApplying.setVisibility(View.VISIBLE);
            mBinding.detailApplying.setText("试用中");
        }else {
            //已结束
            mBinding.detailApply.setText("查看试用报告");
            mBinding.detailApplyAble.setVisibility(View.GONE);
            mBinding.detailApplying.setVisibility(View.VISIBLE);
            mBinding.detailApplying.setText("已结束");
        }
        //商品详情
        mList.addAll(bean.getData().getGoodsContentList());
        mRVAdapter.notifyDataSetChanged();
        //申请流程
        mBinding.detailCheckRule.setText(bean.getData().getCheckRule());
        mBinding.detailVoteGuide.setText(bean.getData().getVoteGuide());
        mBinding.detailActivitiesNote.setText(bean.getData().getActivitiesNote());
        mBinding.detailAgreement.setText(bean.getData().getAgreement());
        mBinding.detailActivitiesProcess.setText(bean.getData().getActivitiesProcess());
        //试用名单
        if (bean.getData().getStatus() == 3){
            //试用中时显示
            String passedUserList = "<font color='#151515' >请以下用户于</font><font color='#E31436' >"
                    + bean.getData().getReportEndTime() +"</font><font color='#151515' >前完成试用报告</font><br>按时提交的优秀试用报告将获得惊喜的奖励";
            mBinding.detailPassedUserList.setText(Html.fromHtml(passedUserList));
            if (bean.getData().getPassedUserList().size() != 0){
                //申请成功人数列表
                mPassedUserListBeans.addAll(bean.getData().getPassedUserList());
                mGVAdapter.notifyDataSetChanged();
                if(mPassedUserListBeans.size() > 6){
                    mBinding.detailPassedMore.setVisibility(View.VISIBLE);
                }else {
                    mBinding.detailPassedMore.setVisibility(View.GONE);
                }
            }else {
                mBinding.detailPassedUserList.setVisibility(View.GONE);
                mBinding.passedUserListGridview.setVisibility(View.GONE);
                mBinding.detailPassedMore.setVisibility(View.GONE);
                mBinding.detailNoPassedUser.setVisibility(View.VISIBLE);
                mBinding.detailNoPassedUser.setText("暂无试用名单");
            }
        }else if (bean.getData().getStatus() == 2){
            //进行中
            mBinding.detailPassedUserList.setVisibility(View.GONE);
            mBinding.passedUserListGridview.setVisibility(View.GONE);
            mBinding.detailPassedMore.setVisibility(View.GONE);
            mBinding.detailNoPassedUser.setVisibility(View.VISIBLE);
            mBinding.detailNoPassedUser.setText("试用名单暂未公布");
        }else {
            //已结束
            mBinding.detailPassedUserList.setVisibility(View.GONE);
            if (bean.getData().getPassedUserList().size() != 0){
                //申请成功人数列表
                mPassedUserListBeans.addAll(bean.getData().getPassedUserList());
                mGVAdapter.notifyDataSetChanged();
                if(mPassedUserListBeans.size() > 6){
                    mBinding.detailPassedMore.setVisibility(View.VISIBLE);
                }else {
                    mBinding.detailPassedMore.setVisibility(View.GONE);
                }
            }else {
                mBinding.passedUserListGridview.setVisibility(View.GONE);
                mBinding.detailPassedMore.setVisibility(View.GONE);
                mBinding.detailNoPassedUser.setVisibility(View.VISIBLE);
                mBinding.detailNoPassedUser.setText("暂无试用名单");
            }
        }
        //分享拉赞人数列表
        if (bean.getData().getApplyUserList().size() != 0){
            mApplyUserListBeans.addAll(bean.getData().getApplyUserList());
            mApplyRVAdapter.notifyDataSetChanged();
            if(mApplyUserListBeans.size() > 7){
                mBinding.detailShareMore.setVisibility(View.VISIBLE);
            }else {
                mBinding.detailShareMore.setVisibility(View.GONE);
            }
        }else {
            mBinding.detailShareMore.setVisibility(View.GONE);
            mBinding.userListRecyclerView.setVisibility(View.GONE);
            mBinding.detailNoShareUser.setVisibility(View.VISIBLE);
        }
        //评论数量
        mBinding.detailComment.setText(bean.getData().getCommentCount() + "");
        //试用报告
        if (bean.getData().getReportArticleList().size() != 0){
            mReportListBeans.addAll(bean.getData().getReportArticleList());
            mReportRVAdapter.notifyDataSetChanged();
            if(mReportListBeans.size() > 7){
                mBinding.detailReportMore.setVisibility(View.VISIBLE);
            }else {
                mBinding.detailReportMore.setVisibility(View.GONE);
            }
        }else {
            mBinding.detailReportMore.setVisibility(View.GONE);
            mBinding.detailNoReport.setVisibility(View.VISIBLE);
            mBinding.reportRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
        initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
    }

    /**
     * 确认申请回调
     */
    @Override
    public void onSuccessApply(TryApplyBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.detailApply.setText("分享拉赞");
        mTryDetailBean.getData().setVoteShareUrl(bean.getVoteShareUrl());//确认申请后更新分享链接

        shareFlag = false;
        shareTitle = mTryDetailBean.getData().getVoteShareTitle();
        shareContent = mTryDetailBean.getData().getVoteShareContent();
        shareImg = mTryDetailBean.getData().getVoteShareImg();
        shareUrl = mTryDetailBean.getData().getVoteShareUrl();
        if (mShareBoardDialog == null) {
            mShareBoardDialog = new ShareBoardDialog();
            mShareBoardDialog.setOnShareListener(this);
        }
        if (!mShareBoardDialog.isAdded()) {
            mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
        }
    }

    @Override
    public void onFileApply(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    public void initError(int id, String title, String content){
        mBinding.detailNoDate.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    /**
     * 试用报告item点击
     */
    @Override
    public void onItemReportClick(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mReportListBeans.get(position).getPv());
            mReportListBeans.get(position).setPv((bigDecimal.intValue() + 1));
            mReportRVAdapter.notifyDataSetChanged();
            if (mReportListBeans.get(position).getContentType() == 1){//试用报告：web
                startActivity(new Intent(this,ArticleDetailActivity.class)
                        .putExtra("id",mReportListBeans.get(position).getArticleId())
                        .putExtra("type","4")
                );
            }else  if (mReportListBeans.get(position).getContentType() == 3){//试用报告：json
                startActivity(new Intent(this,ReportDetailActivity.class)
                        .putExtra("id",mReportListBeans.get(position).getArticleId())
                        .putExtra("type","4")
                );
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
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
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

    private void setCountDownTimer(int day, int hour ,int min , int second , int applied) {
        long time =( ( ( ( ( day * 24 ) + hour) * 60 )  + min ) *  60) + second;
        mCountDownTimerUtils =  new CountDownTimer(time * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                int ss = 1000;
                int mi = ss * 60;
                int hh = mi * 60;
                int dd = hh * 24;

                long day = millisUntilFinished / dd;
                long hour = ((millisUntilFinished - day * dd) / hh);
                long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
                long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
                long milliSecond = millisUntilFinished  - hour * hh - minute * mi - second * ss - day * dd;

                String s_day = day + "";
                String s_hour=hour+"";
                String s_minute=minute+"";
                String s_second=second+"";
                if(s_hour.length()==1){
                    s_hour="0"+s_hour;
                }
                if(s_second.length()==1){
                    s_second="0"+s_second;
                }
                if(s_minute.length()==1){
                    s_minute="0"+s_minute;
                }
                if(s_day.length() == 1){
                    s_day = "0" + s_day;
                }
                mBinding.detailTimeDay0.setText(s_day.substring(0,1));
                mBinding.detailTimeDay1.setText(s_day.substring(1,2));
                mBinding.detailTimeHour0.setText(s_hour.substring(0,1));
                mBinding.detailTimeHour1.setText(s_hour.substring(1,2));
                mBinding.detailTimeMin0.setText(s_minute.substring(0,1));
                mBinding.detailTimeMin1.setText(s_minute.substring(1,2));
                mBinding.detailTimeSecond0.setText(s_second.substring(0,1));
                mBinding.detailTimeSecond1.setText(s_second.substring(1,2));
            }

            public void onFinish() {
                mBinding.detailTimeDay0.setText("0");
                mBinding.detailTimeDay1.setText("0");
                mBinding.detailTimeDay0.setText("0");
                mBinding.detailTimeDay1.setText("0");
                mBinding.detailTimeHour0.setText("0");
                mBinding.detailTimeHour1.setText("0");
                mBinding.detailTimeMin0.setText("0");
                mBinding.detailTimeMin1.setText("0");
                mBinding.detailTimeSecond0.setText("0");
                mBinding.detailTimeSecond1.setText("0");
                mBinding.detailApply.setText("查看名单");
                mBinding.detailApplyAble.setVisibility(View.GONE);
                mBinding.detailApplying.setVisibility(View.VISIBLE);
                mBinding.detailApplying.setText("试用中");
                EventBus.getDefault().post(new TryStatusBus(getIntent().getIntExtra("pos",-1)));
            }
        }.start();
    }

    //获取评论列表_试用报告详情
    @Subscribe
    public void commentResher(TryShopBus tryShopBus) {
        if (tryShopBus != null) {
            mBinding.detailComment.setText(tryShopBus.getCount() + "");
        }
    }

    /**
     * 每日任务里点击跳转后，需要关闭的页面
     */
    @Subscribe
    public void changePage(ChangeHomePageBus bus){
        if(bus != null){
            finish();
        }
    }
}
