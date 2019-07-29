package com.example.administrator.jipinshop.activity.tryout.freedetail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.CommenBannerAdapter;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.databinding.ActivityFreeDetailBinding;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopDescriptionFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopRuleFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.detail.ShopUserFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.html.CustomerTagHandler_1;
import com.example.administrator.jipinshop.util.html.HtmlParser;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.FreeDetailDialog;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/21
 * @Describe 免单购详情
 */
public class FreeDetailActivity extends BaseActivity implements View.OnClickListener, FreeDetailView, ShareBoardDialog.onShareListener, FreeDetailDialog.OnItem {

    @Inject
    FreeDetailPresenter mPresenter;
    private ActivityFreeDetailBinding mBinding;
    private String id = "";
    private Dialog mDialog;
    //倒计时，所有地方的倒计时都用同一个，因为不重叠
    private CountDownTimer mCountDownTimer;
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
    //三个fragment
    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;
    float startX = 0;
    float startY = 0;
    float xDistance = 0;
    float yDistance = 0;
    /**
     * 分享的图片
     */
    private String shareImage = "";
    private String shareName = "";
    private String shareContent = "";
    private String shareUrl = "";
    //分享面板
    private ShareBoardDialog mShareBoardDialog;
    //免单状态
    private int applyStatus = -1;
    private int status = 0;
    //获取goodsId
    private String goodsId = "";
    private int Point = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_free_detail);
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
        id = getIntent().getStringExtra("id");
        mPresenter.setTitle(mBinding.appbar,mBinding.detailBack,mBinding.statusBar);
        mPresenter.init(this,mBinding.detailPagerContainer,mBinding.statusBar,mBinding.statusBar1,mBinding.titleContainer);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        //banner
        mBannerAdapter = new CommenBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBinding.viewPager.setAdapter(mBannerAdapter);
        //初始化
        mFragments = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());
        //请求数据
        mPresenter.getDate(id,this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
            case R.id.title_back:
                finish();
                break;
            case R.id.title_favor://分享
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = new ShareBoardDialog();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.detail_bottomBuy:
                if (mBinding.detailBottomBuy.getText().toString().equals("即将开始")){
                    ToastUtil.show("活动暂未开始");
                }else if (mBinding.detailBottomBuy.getText().toString().equals("免费抢购")){
                    //弹框
                    if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                        startActivity(new Intent(this, LoginActivity.class));
                        return;
                    }
                    if (Point <= SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0)){
                        //可以参加
                        FreeDetailDialog appDialog = FreeDetailDialog.getInstance(Point + "", 1);
                        appDialog.setOnItem(this);
                        if (!appDialog.isAdded()) {
                            appDialog.show(getSupportFragmentManager(), "FreeDetailDialog");
                        }
                    }else {
                        //不可以参加
                        FreeDetailDialog appDialog = FreeDetailDialog.getInstance(Point + "", 0);
                        if (!appDialog.isAdded()) {
                            appDialog.show(getSupportFragmentManager(), "FreeDetailDialog");
                        }
                    }
                }else if (mBinding.detailBottomBuy.getText().toString().equals("前往购买")){
                    if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                        startActivity(new Intent(this, LoginActivity.class));
                        return;
                    }
                    if (applyStatus == 1){
                        //二次购买的情况（可再次购买但不返回佣金）  跳转到淘宝
                        if (TextUtils.isEmpty(goodsId)){
                            ToastUtil.show("暂无商品链接");
                            return;
                        }
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                        if(mDialog != null && !mDialog.isShowing()){
                            mDialog.show();
                        }
                        String specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId,"");
                        if (TextUtils.isEmpty(specialId) || specialId.equals("null")){
                            startActivity(new Intent(this, WebActivity.class)
                                    .putExtra(WebActivity.url, RetrofitModule.UP_BASE_URL+"qualityshop-api/api/taobao/login?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                                    .putExtra(WebActivity.title,"淘宝授权")
                            );
                        }else {
                            mPresenter.goodsBuyLink(goodsId,this.bindToLifecycle());
                        }
                    }
                }else if (mBinding.detailBottomBuy.getText().toString().contains("可返")){
                    //第一次申请参加成功，但未付款  跳转到淘宝
                    if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                        startActivity(new Intent(this, LoginActivity.class));
                        return;
                    }
                    if (TextUtils.isEmpty(goodsId)){
                        ToastUtil.show("暂无商品链接");
                        return;
                    }
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    if(mDialog != null && !mDialog.isShowing()){
                        mDialog.show();
                    }
                    String specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId,"");
                    if (TextUtils.isEmpty(specialId) || specialId.equals("null")){
                        startActivity(new Intent(this, WebActivity.class)
                                .putExtra(WebActivity.url, RetrofitModule.UP_BASE_URL+"qualityshop-api/api/taobao/login?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                                .putExtra(WebActivity.title,"淘宝授权")
                        );
                    }else {
                        mPresenter.goodsBuyLink(goodsId,this.bindToLifecycle());
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(FreeDetailBean detailBean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.setDate(detailBean.getData());
        mBinding.detailBuyOtherPrice.setTv(true);
        mBinding.detailBuyOtherPrice.setColor(R.color.color_white);
//        mBinding.itemOtherPrice.setTv(true);
//        mBinding.itemOtherPrice.setColor(R.color.color_ACACAC);
        mBinding.itemPrice.setTv(true);
        mBinding.itemPrice.setColor(R.color.color_ACACAC);
        mBinding.itemProgressbar.setTotalAndCurrentCount(detailBean.getData().getCount(),detailBean.getData().getUserCount());
        if(detailBean.getData().getImgList().size() == 1){
            mBinding.viewPager.setVisibility(View.GONE);
            mBinding.detailPoint.setVisibility(View.GONE);
            mBinding.pagerImage.setVisibility(View.VISIBLE);
            GlideApp.loderImage(this,detailBean.getData().getImgList().get(0),mBinding.pagerImage,0,0);
        }else {
            mBinding.viewPager.setVisibility(View.VISIBLE);
            mBinding.detailPoint.setVisibility(View.VISIBLE);
            mBinding.pagerImage.setVisibility(View.GONE);
            mBannerList.addAll(detailBean.getData().getImgList());
            mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
            new Thread(new MyRunble()).start();
        }
        if (detailBean.getData().getApplyStatus() == 0){
            //活动进行中且申请过后未付款的
            mBinding.detailBottomNote.setVisibility(View.VISIBLE);
            String html = "前往购买<font size='13'>(可返"+ detailBean.getData().getFreePrice() +"元)</font>";
            mBinding.detailBottomBuy.setText(HtmlParser.buildSpannedText(html,new CustomerTagHandler_1()));
            mBinding.detailBottomBuy.setBackgroundColor(getResources().getColor(R.color.color_E31B3C));
            long timer = dateAddOneDay(detailBean.getData().getDendlineTime()) - System.currentTimeMillis();
            if (timer > 0) {
                mCountDownTimer = new CountDownTimer(timer, 1000) {
                    public void onTick(long millisUntilFinished) {
                        mBinding.detailBuyTime.setText("仅剩："+ getCountTimeByLong(millisUntilFinished,true));
                    }
                    public void onFinish() {
                        mBinding.detailBottomBuy.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
                        mBinding.detailBottomBuy.setText("前往购买");
                        mBinding.detailBottomNote.setVisibility(View.GONE);
                    }
                }.start();
            }else {
                mBinding.detailBottomBuy.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
                mBinding.detailBottomBuy.setText("前往购买");
                mBinding.detailBottomNote.setVisibility(View.GONE);
            }
        }else {
            mBinding.detailBottomNote.setVisibility(View.GONE);
        }
        if (detailBean.getData().getApplyStatus() == -1 && detailBean.getData().getStatus() == 0){//即将开始
            long timer = dateAddOneDay(detailBean.getData().getActivitiesStartTime()) - System.currentTimeMillis();
            if (timer > 0) {
                mCountDownTimer = new CountDownTimer(timer, 1000) {
                    public void onTick(long millisUntilFinished) {
                        mBinding.detailCountdown.setText("开抢倒计时："+ getCountTimeByLong(millisUntilFinished,false));
                    }
                    public void onFinish() {
                        detailBean.getData().setStatus(1);
                        detailBean.getData().setApplyStatus(-1);
                        mBinding.setDate(detailBean.getData());
                    }
                }.start();
            }else {
                detailBean.getData().setStatus(1);
                detailBean.getData().setApplyStatus(-1);
                mBinding.setDate(detailBean.getData());
            }
        }
        mFragments.add(ShopDescriptionFragment.getInstance(new Gson().toJson(detailBean.getData().getGoodsContentList(),new TypeToken<List<FreeDetailBean.DataBean.GoodsContentListBean>>(){}.getType())));
        mFragments.add(ShopUserFragment.getInstance(detailBean.getData().getId()));
        mFragments.add(ShopRuleFragment.getInstance(detailBean.getData().getFreeGuide()));
        mHomeAdapter.setFragments(mFragments);
        mBinding.detailViewpager.setAdapter(mHomeAdapter);
        mBinding.detailViewpager.setOffscreenPageLimit(mFragments.size() - 1);
        mBinding.tabLayout.setupWithViewPager(mBinding.detailViewpager);
        mPresenter.initTabLayout(this,mFragments,mBinding.tabLayout);
        shareImage = detailBean.getData().getShareImg();
        shareContent = detailBean.getData().getShareContent();
        shareName = detailBean.getData().getShareTitle();
        shareUrl = detailBean.getData().getShareUrl();
        status = detailBean.getData().getStatus();
        applyStatus = detailBean.getData().getApplyStatus();
        goodsId = detailBean.getData().getGoodsId();
        Point = detailBean.getData().getPoint();
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onBuyLinkSuccess(ImageBean bean) {
        TaoBaoUtil.openAliHomeWeb(this,bean.getData());
    }

    /**
     * 申请成功
     */
    @Override
    public void onApplySuccess(FreeDetailBean detailBean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show("参与成功");
        detailBean.getData().setApplyStatus(0);
        detailBean.getData().setUserCount(detailBean.getData().getUserCount() + 1);
        mBinding.setDate(detailBean.getData());
        mBinding.detailBottomNote.setVisibility(View.VISIBLE);
        String html = "前往购买<font size='13'>(可返"+ detailBean.getData().getFreePrice() +"元)</font>";
        mBinding.detailBottomBuy.setText(HtmlParser.buildSpannedText(html,new CustomerTagHandler_1()));
        mBinding.detailBottomBuy.setBackgroundColor(getResources().getColor(R.color.color_E31B3C));
        long temp = System.currentTimeMillis();
        long currentTime = temp + 60 * 60 * 1000;
        long timer = currentTime - temp;
        if (timer > 0) {
            mCountDownTimer = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    mBinding.detailBuyTime.setText("仅剩："+ getCountTimeByLong(millisUntilFinished,true));
                }
                public void onFinish() {
                    mBinding.detailBottomBuy.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
                    mBinding.detailBottomBuy.setText("前往购买");
                    mBinding.detailBottomNote.setVisibility(View.GONE);
                }
            }.start();
        }else {
            mBinding.detailBottomBuy.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
            mBinding.detailBottomBuy.setText("前往购买");
            mBinding.detailBottomNote.setVisibility(View.GONE);
        }
    }

    public long dateAddOneDay(String time){
        long currentTime = 0l;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            currentTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public String getCountTimeByLong(long millisUntilFinished,Boolean falg){
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = millisUntilFinished / dd;
        long hour = ((millisUntilFinished - day * dd) / hh);
        long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
        long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
        long milliSecond = millisUntilFinished  - hour * hh - minute * mi - second * ss - day * dd;

        String result = "";
        if (falg){
            result = minute + "分" + second + "秒";
        }else {
            if(day != 0){
                result = day + "天" + hour + "小时" + minute + "分钟" + second + "秒";
            }else if(hour != 0){
                result = hour + "小时" + minute + "分钟" + second + "秒";
            }else if(minute != 0){
                result = minute + "分钟" + second + "秒";
            }else if(second != 0){
                result = second + "秒";
            }
        }

        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
        stopThread = false;
        mHandler.removeCallbacksAndMessages(null);
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, shareUrl, shareName, shareContent, shareImage, R.mipmap.share_logo);
    }

    /**
     * 确认参与
     */
    @Override
    public void onItemClick() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        if(mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
        mPresenter.freeApply(id,this.bindToLifecycle());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
     */
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

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
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

    public AppBarLayout getBar() {
        return mBinding.appbar;
    }
}
