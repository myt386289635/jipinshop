package com.example.administrator.jipinshop.fragment.member;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alipay.sdk.app.PayTask;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.member.family.FamilyActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.adapter.MemberFreeAdapter;
import com.example.administrator.jipinshop.adapter.MemberShopAdapter;
import com.example.administrator.jipinshop.adapter.MemberSignAdapter;
import com.example.administrator.jipinshop.adapter.MemberVideoAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.PayResultBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.bean.eventbus.PayBus;
import com.example.administrator.jipinshop.databinding.FragmentMemberNewBinding;
import com.example.administrator.jipinshop.fragment.member.cheap.CheapFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.anim.AnimationUtils;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.MemberBuyPop;
import com.example.administrator.jipinshop.view.dialog.MemberRenewPop;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/8/25
 * @Describe 新版会员页面
 */
public class MemberFragment extends DBBaseFragment implements View.OnClickListener, OnRefreshListener, MemberView, MemberBuyPop.OnClick, MemberRenewPop.OnClick, ViewPager.OnPageChangeListener, MemberVideoAdapter.OnItem {

    @Inject
    MemberPresenter mPresenter;
    @Inject
    AppStatisticalUtil appStatisticalUtil;

    private FragmentMemberNewBinding mBinding;
    private String type = "1";// 1:fragment 2:activity
    private Boolean once = true;
    private String otherGoodsId = "";
    private String source = "";
    private MemberBuyPop mBuyPop;//购买弹窗
    private MemberRenewPop mRenewPop;//续费弹窗
    private String monthPrice = "";
    private String monthPriceBefore = "";
    private String yearPrice = "";
    private String yearPriceBefore = "";
    private String preMonthEndTime = "";
    private String preYearEndTime = "";
    private IWXAPI msgApi;//微信支付
    private String level;//1是月卡 2是年卡
    private Dialog mDialog;
    private String endTime = "";//付款后的会员到期时间
    private int userLevel = 0;//用户身份的
    private int openFamily = 1; //0关闭，1开启
    //更多权益
    private List<MemberNewBean.DataBean.VipBoxListBean> monthBoxList;
    private List<MemberNewBean.DataBean.VipBoxListBean> yearBoxList;
    //广告
    private List<MemberNewBean.DataBean.MessageListBean> mAdList;
    //每月0元购
    private List<MemberNewBean.DataBean.LevelDetail3Bean.ListBean> mFreeList;
    private MemberFreeAdapter mFreeAdapter;
    //商品列表
    private List<MemberNewBean.DataBean.OrderLevelDataBean.OrderListBean> mOrderList;
    private MemberShopAdapter mShopAdapter;
    //特惠购列表
    private List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> mCheapList;
    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;
    private List<ImageView> point;
    //会员极币任务
    private List<MemberNewBean.DataBean.LevelDetail6Bean.ListBeanXX> mSignList;
    private MemberSignAdapter mSignAdapter;
    //视频会员月卡
    private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> mVideoList;
    private MemberVideoAdapter mVideoAdapter;
    //吃喝玩乐
    private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> mPlayList;
    private MemberVideoAdapter mPlayAdapter;
    //支付宝支付回调
    private Handler.Callback mCallback = msg -> {
        if (msg.what == 101){
            //支付宝支付回调
            PayResultBean payResult = new PayResultBean((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                //成功
                DialogUtil.paySucDialog(getContext(),endTime);
            } else {
                //失败
                DialogUtil.payFileDialog(getContext(),userLevel, type -> {
                    onBuyMember(level,type);
                });
            }
        }
        return true;
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean isSpace = false;//是否展开的。默认为false

    public static MemberFragment getInstance(String type){
        MemberFragment fragment = new MemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);//1:fragment 2:activity
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_new,container,false);
        mBinding.setListener(this);
        EventBus.getDefault().register(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,mBinding.statusBar2,getContext());
        mBinding.swipeToLoad.setOnRefreshListener(this);
        type = getArguments().getString("type","1");

        //初始化微信支付
        msgApi = WXAPIFactory.createWXAPI(getContext(), null);
        msgApi.registerApp("wxfd2e92db2568030a");

        //初始化pop
        monthBoxList = new ArrayList<>();
        yearBoxList = new ArrayList<>();
        mBuyPop = new MemberBuyPop(getContext(),this);
        mRenewPop = new MemberRenewPop(getContext(),this);

        //广告
        mAdList = new ArrayList<>();
        //商品列表
        mOrderList = new ArrayList<>();
        mShopAdapter = new MemberShopAdapter(mOrderList,getContext());
        mBinding.memberShopList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.memberShopList.setNestedScrollingEnabled(false);
        mBinding.memberShopList.setAdapter(mShopAdapter);
        mBinding.memberShopList.setFocusable(false);//拒绝首次进入页面时滑动
        //每月0元购
        mFreeList = new ArrayList<>();
        mBinding.memberFree.setLayoutManager(new GridLayoutManager(getContext(),3));
        mBinding.memberFree.setNestedScrollingEnabled(false);
        mFreeAdapter = new MemberFreeAdapter(mFreeList,getContext());
        mBinding.memberFree.setAdapter(mFreeAdapter);
        mBinding.memberFree.setFocusable(false);//拒绝首次进入页面时滑动
        //特惠购列表
        point = new ArrayList<>();
        mCheapList = new ArrayList<>();
        mFragments = new ArrayList<>();
        mFragments.add(CheapFragment.getInstence(0));
        mFragments.add(CheapFragment.getInstence(6));
        mHomeAdapter = new HomeAdapter(getChildFragmentManager());
        mHomeAdapter.setFragments(mFragments);
        mBinding.memberCheap.setAdapter(mHomeAdapter);
        mPresenter.initBanner(mFragments,getContext(),point,mBinding.memberPoint);
        mBinding.memberCheap.addOnPageChangeListener(this);
        //会员极币任务
        mSignList = new ArrayList<>();
        mSignAdapter = new MemberSignAdapter(mSignList,getContext());
        mBinding.memberSign.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.memberSign.setNestedScrollingEnabled(false);
        mBinding.memberSign.setAdapter(mSignAdapter);
        mBinding.memberSign.setFocusable(false);
        //视频
        mVideoList = new ArrayList<>();
        mVideoAdapter = new MemberVideoAdapter(mVideoList,getContext());
        mBinding.memberVideo.setLayoutManager(new GridLayoutManager(getContext(),4));
        mBinding.memberVideo.setNestedScrollingEnabled(false);
        mBinding.memberVideo.setAdapter(mVideoAdapter);
        mBinding.memberVideo.setFocusable(false);
        //吃喝玩了
        mPlayList = new ArrayList<>();
        mPlayAdapter = new MemberVideoAdapter(mPlayList,getContext());
        mPlayAdapter.setOnItem(this);
        mBinding.memberPlay.setLayoutManager(new GridLayoutManager(getContext(),4));
        mBinding.memberPlay.setNestedScrollingEnabled(false);
        mBinding.memberPlay.setAdapter(mPlayAdapter);
        mBinding.memberPlay.setFocusable(false);

        if (type.equals("2")){
            SPUtils.getInstance().put(CommonDate.memberNotice, false);
            mBinding.swipeToLoad.post(() -> {
                mBinding.swipeToLoad.setRefreshing(true);
            });
            mBinding.memberBlack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.member_share:
                //规则说明
                startActivity(new Intent(getContext(), WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "new-free/memberrule")
                        .putExtra(WebActivity.title, "规则说明")
                );
                break;
            case R.id.member_black:
                if (getActivity() != null){
                    getActivity().finish();
                }
                break;
            case R.id.member_month:
                //购买月卡
                if (TextUtils.isEmpty(monthPrice) || TextUtils.isEmpty(monthPriceBefore)){
                    ToastUtil.show("网络错误，请稍后尝试");
                    return;
                }
                appStatisticalUtil.addEvent("yueka.click", this.bindToLifecycle());
                AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_SHOW,100);
                mBuyPop.show(mBinding.memberPayContainer,"1",monthPrice,monthPriceBefore);
                break;
            case R.id.member_year:
                //购买年卡
                if (TextUtils.isEmpty(yearPrice) || TextUtils.isEmpty(yearPriceBefore)){
                    ToastUtil.show("网络错误，请稍后尝试");
                    return;
                }
                appStatisticalUtil.addEvent("nianka.click", this.bindToLifecycle());
                AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_SHOW,100);
                mBuyPop.show(mBinding.memberPayContainer,"2",yearPrice,yearPriceBefore);
                break;
            case R.id.member_rule:
                //会员规则
                startActivity(new Intent(getContext(), WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "new-free/memberAgreement")
                        .putExtra(WebActivity.title, "会员协议")
                );
                break;
            case R.id.member_userPay:
                //续费
                AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_SHOW,100);
                mRenewPop.show(mBinding.memberPayContainer,monthPrice,monthPriceBefore,yearPrice,yearPriceBefore,
                        preYearEndTime,preMonthEndTime,yearBoxList,monthBoxList);
                break;
            case R.id.member_adContainer:
                //人员广告
                startActivity(new Intent(getContext(), InvitationNewActivity.class));
                break;
            case R.id.member_copyServer:
            case R.id.member_copy:
                //复制导师微信
                ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", mBinding.memberWxCode.getText().toString().replace(getResources().getString(R.string.member_wx),""));
                clip.setPrimaryClip(clipData);
                SPUtils.getInstance().put(CommonDate.CLIP,  mBinding.memberWxCode.getText().toString().replace(getResources().getString(R.string.member_wx),""));
                ToastUtil.show("复制成功");
                break;
            case R.id.member_shop2Container:
                //跳转到特惠购
                startActivity(new Intent(getContext(), CheapBuyActivity.class));
                break;
            case R.id.member_shop1Container:
                //商品详情
                if (TextUtils.isEmpty(otherGoodsId)){
                    return;
                }
                startActivity(new Intent(getContext(), TBShoppingDetailActivity.class)
                        .putExtra("otherGoodsId", otherGoodsId)
                        .putExtra("source",source)
                );
                break;
            case R.id.member_shadow:
                AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_HIDDEN,100);
                if (mBuyPop != null && mBuyPop.isShowing())
                    mBuyPop.dismiss();
                if (mRenewPop != null && mRenewPop.isShowing())
                    mRenewPop.dismiss();
                break;
            case R.id.member_playUp:
            case R.id.member_playMore:
                //吃喝玩乐展开更多
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.memberPlayContainer.getLayoutParams();
                if (!isSpace){
                    //未展开
                    layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    isSpace  = true;
                    mBinding.memberPlayUp.setVisibility(View.VISIBLE);
                    mBinding.memberPlayMore.setVisibility(View.GONE);
                }else {
                    //已展开
                    layoutParams.height = (int) getResources().getDimension(R.dimen.y693);
                    isSpace = false;
                    mBinding.memberPlayMore.setVisibility(View.VISIBLE);
                    mBinding.memberPlayUp.setVisibility(View.GONE);
                }
                mBinding.memberPlayContainer.setLayoutParams(layoutParams);
                break;
            case R.id.member_family:
                //全家共享
                if (openFamily == 1){
                    startActivity(new Intent(getContext(), FamilyActivity.class)
                            .putExtra("userLevel", userLevel)
                    );
                }
                break;
            case R.id.guide_next1Container:
                mBinding.guideNext1Container.setVisibility(View.GONE);
                mBinding.guideNext2Container.setVisibility(View.VISIBLE);
                mBinding.guideNext3Container.setVisibility(View.GONE);
                break;
            case R.id.guide_next2Container:
                mBinding.guideNext1Container.setVisibility(View.GONE);
                mBinding.guideNext2Container.setVisibility(View.GONE);
                mBinding.guideNext3Container.setVisibility(View.VISIBLE);
                break;
            case R.id.guide_goto:
            case R.id.guide_next3Container:
                mBinding.memberGuideContainer.setVisibility(View.GONE);
                if (type.equals("1")){
                    ((MainActivity)getActivity()).memberGuide(false);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.levelIndex(this.bindToLifecycle());
    }

    @Override
    public void onSuccess(MemberNewBean bean) {
        mBinding.swipeToLoad.setRefreshing(false);

        otherGoodsId = bean.getData().getLevelDetail1().getOtherGoodsId();
        source = bean.getData().getLevelDetail1().getSource();
        monthPrice = bean.getData().getMonthPrice();
        monthPriceBefore = bean.getData().getMonthPriceBefore();
        yearPrice = bean.getData().getYearPrice();
        yearPriceBefore = bean.getData().getYearPriceBefore();
        monthBoxList.clear();
        monthBoxList.addAll(bean.getData().getMonthBoxList());
        yearBoxList.clear();
        yearBoxList.addAll(bean.getData().getYearBoxList());
        preMonthEndTime = bean.getData().getPreMonthEndTime();
        preYearEndTime = bean.getData().getPreYearEndTime();
        openFamily = bean.getOpenFamily();

        mBinding.setBean(bean.getData());
        mBinding.executePendingBindings();
        mBinding.memberMonthOtherCost.setTv(true);
        mBinding.memberMonthOtherCost.setColor(R.color.color_white);
        mBinding.memberYearOtherCost.setTv(true);
        mBinding.memberYearOtherCost.setColor(R.color.color_white);
        GlideApp.loderImage(getContext(),bean.getData().getBoxImg(),mBinding.memberMore,0,0);
        //广告
        mAdList.clear();
        mAdList.addAll(bean.getData().getMessageList());
        mPresenter.adFlipper(getContext(),mBinding.viewFlipper,mAdList);
        //商品列表
        mOrderList.clear();
        if (bean.getData().getOrderLevelData() != null){
            mOrderList.addAll(bean.getData().getOrderLevelData().getOrderList());
        }
        mShopAdapter.notifyDataSetChanged();
        //每月0元购
        mFreeList.clear();
        mFreeList.addAll(bean.getData().getLevelDetail3().getList());
        mFreeAdapter.setUserLevel(bean.getData().getLevel());
        mFreeAdapter.notifyDataSetChanged();
        if (TextUtils.isEmpty(bean.getData().getLevelDetail3().getTitle3())){
            mBinding.memberFreeTitle.setVisibility(View.GONE);
        }else {
            mBinding.memberFreeTitle.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail3().getTitle4())){
            mBinding.memberFreeFee.setVisibility(View.GONE);
        }else {
            mBinding.memberFreeFee.setVisibility(View.VISIBLE);
        }
        //特惠购列表
        mCheapList.clear();
        mCheapList.addAll(bean.getData().getLevelDetail4().getList());
        ((CheapFragment)mFragments.get(0)).setDate(mCheapList);
        ((CheapFragment)mFragments.get(1)).setDate(mCheapList);
        if (mCheapList.get(5).getStatus() == 0 || mCheapList.get(5).getStatus() == 2){
            mBinding.memberCheap.setCurrentItem(0);
        }else {
            mBinding.memberCheap.setCurrentItem(1);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail4().getTitle3())){
            mBinding.memberMonthTitle.setVisibility(View.GONE);
        }else {
            mBinding.memberMonthTitle.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail4().getTitle4())){
            mBinding.memberMonthFee.setVisibility(View.GONE);
        }else {
            mBinding.memberMonthFee.setVisibility(View.VISIBLE);
        }
        //会员极币任务
        mSignList.clear();
        mSignList.addAll(bean.getData().getLevelDetail6().getList());
        mSignAdapter.notifyDataSetChanged();
        //一家人共享
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(0),mBinding.memberFamilyOne,0,0);
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(1),mBinding.memberFamilyTwo,0,0);
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(2),mBinding.memberFamilyThree,0,0);
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(3),mBinding.memberFamilyFore,0,0);
        if (TextUtils.isEmpty(bean.getData().getLevelDetail5().getTitle3())){
            mBinding.memberFamilyTitle.setVisibility(View.GONE);
        }else {
            mBinding.memberFamilyTitle.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail5().getTitle4())){
            mBinding.memberFamilyFee.setVisibility(View.GONE);
        }else {
            mBinding.memberFamilyFee.setVisibility(View.VISIBLE);
        }
        //视频
        mVideoList.clear();
        mVideoList.addAll(bean.getData().getLevelDetail7().getList());
        mVideoAdapter.notifyDataSetChanged();
        if (TextUtils.isEmpty(bean.getData().getLevelDetail7().getTitle3())){
            mBinding.memberVideoTitle.setVisibility(View.GONE);
        }else {
            mBinding.memberVideoTitle.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail7().getTitle4())){
            mBinding.memberVideoFee.setVisibility(View.GONE);
        }else {
            mBinding.memberVideoFee.setVisibility(View.VISIBLE);
        }
        if (bean.getData().getLevel() == 2 && bean.getData().getLevelDetail7().getTitle3().equals("待领取")){
            mBinding.memberServerContainer.setVisibility(View.VISIBLE);
        }else {
            mBinding.memberServerContainer.setVisibility(View.GONE);
        }
        //吃喝玩了
        mPlayList.clear();
        mPlayList.addAll(bean.getData().getLevelDetail8().getList());
        mPlayAdapter.notifyDataSetChanged();
        mBinding.memberPlayUp.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.memberPlayContainer.getLayoutParams();
        if (mPlayList.size() > 16){
            isSpace = false;
            mBinding.memberPlayMore.setVisibility(View.VISIBLE);
            layoutParams.height = (int) getResources().getDimension(R.dimen.y693);
        }else {
            isSpace = true;
            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            mBinding.memberPlayContainer.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.y40));
            mBinding.memberPlayMore.setVisibility(View.GONE);
        }
        mBinding.memberPlayContainer.setLayoutParams(layoutParams);
        //身份处理  0 普通 ， 1 月卡 ，2年卡
        userLevel = bean.getData().getLevel();
        if (bean.getData().getLevel() == 0){
            mBinding.memberPayContainer.setVisibility(View.VISIBLE);
            mBinding.memberAdContainer.setVisibility(View.VISIBLE);
            mBinding.memberMemberContainer.setVisibility(View.GONE);
            mBinding.memberTitle1.setText("开通VIP可享更多权益");
            mBinding.memberVipContainer.setVisibility(View.VISIBLE);
            if (SPUtils.getInstance().getBoolean(CommonDate.memberGuide, true)){
                //第一次登陆是非会员，出来新手指导
                mBinding.memberGuideContainer.setVisibility(View.VISIBLE);
                mBinding.guideNext1Container.setVisibility(View.VISIBLE);
                mBinding.guideNext2Container.setVisibility(View.GONE);
                mBinding.guideNext3Container.setVisibility(View.GONE);
                if (type.equals("1")){
                    ((MainActivity)getActivity()).memberGuide(true);
                }
                SPUtils.getInstance().put(CommonDate.memberGuide,false);
            }
        }else {
            mBinding.memberVipContainer.setVisibility(View.GONE);
            mBinding.memberPayContainer.setVisibility(View.GONE);
            mBinding.memberAdContainer.setVisibility(View.GONE);
            mBinding.memberMemberContainer.setVisibility(View.VISIBLE);
            if (bean.getData().getLevel() == 1){
                mBinding.memberTitle1.setText("月卡VIP特权");
                mBinding.memberTab.setImageResource(R.mipmap.member1_month_tab);
                mBinding.memberMemberContainer.setBackgroundResource(R.mipmap.member1_month_bg);
                mBinding.memberUserName.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
                mBinding.memberUserTime.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
                mBinding.memberUserPay.setBackgroundResource(R.drawable.bg_e8a971);
                mBinding.memberUserPay.setTextColor(getContext().getResources().getColor(R.color.color_white));
            }else {
                mBinding.memberTitle1.setText("年卡VIP特权");
                mBinding.memberTab.setImageResource(R.mipmap.member1_year_tab);
                mBinding.memberMemberContainer.setBackgroundResource(R.mipmap.member1_year_bg);
                mBinding.memberUserName.setTextColor(getContext().getResources().getColor(R.color.color_433A37));
                mBinding.memberUserTime.setTextColor(getContext().getResources().getColor(R.color.color_433A37));
                mBinding.memberUserPay.setBackgroundResource(R.drawable.bg_342f2f);
                mBinding.memberUserPay.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
            }
            if (SPUtils.getInstance().getBoolean(CommonDate.memberGuide, true)){
                SPUtils.getInstance().put(CommonDate.memberGuide,false);//第一次登陆是会员，之后就不再出现新手指导
            }
        }
        once = false;
    }

    @Override
    public void onFile(String error) {
        mBinding.swipeToLoad.setRefreshing(false);
        ToastUtil.show(error);
        once = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!once && !TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            mPresenter.levelIndex(this.bindToLifecycle());
        }
    }

    ///level 1是月卡 2是年卡    //type 1是支付宝 2是微信
    @Override
    public void onBuyMember(String level, String type) {
        this.level = level;
        AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_HIDDEN,100);
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        if (type.equals("1")){
            mPresenter.alipay(level,this.bindToLifecycle());
        }else {
            mPresenter.wxpay(level,this.bindToLifecycle());
        }
    }

    @Override
    public void onWxPay(WxPayBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        endTime = bean.getEndTime();//支付后的会员到期时间
        PayReq request = new PayReq();
        request.appId = bean.getData().getAppid();
        request.partnerId = bean.getData().getPartnerid();
        request.prepayId = bean.getData().getPrepayid();
        request.packageValue = bean.getData().getPackageValue();
        request.nonceStr = bean.getData().getNoncestr();
        request.timeStamp = bean.getData().getTimestamp();
        request.sign = bean.getData().getSign();
        msgApi.sendReq(request);
    }

    @Override
    public void onAlipay(ImageBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        endTime = bean.getEndTime();//支付后的会员到期时间
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(getActivity());
            Map<String,String> result = alipay.payV2(bean.getData(),true);
            Message msg = new Message();
            msg.what = 101;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        //必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onCommenFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onDismiss() {
        AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_HIDDEN,100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onPayResult(PayBus bus){
        if (bus != null){
            if (type.equals(bus.getFlag())){
                if (bus.getType().equals(WXPayEntryActivity.pay_success)) {
                    DialogUtil.paySucDialog(getContext(), endTime);
                } else if (bus.getType().equals(WXPayEntryActivity.pay_faile)) {
                    DialogUtil.payFileDialog(getContext(),userLevel,  type -> {
                        onBuyMember(level, type);
                    });
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < point.size(); i++) {
            if (i == position % mFragments.size()){
                point.get(i).setImageResource(R.drawable.banner_down3);
            }else {
                point.get(i).setImageResource(R.drawable.banner_up3);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    //吃喝玩乐跳转
    @Override
    public void onItem(int position) {
        if (userLevel == 0){
            ToastUtil.show("仅限VIP会员享受优惠");
            return;
        }
        ShopJumpUtil.openBanner(getContext(),mPlayList.get(position).getType(),
                mPlayList.get(position).getTargetId(),mPlayList.get(position).getTitle(),
                mPlayList.get(position).getSource());
    }
}
