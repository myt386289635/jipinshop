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
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alipay.sdk.app.PayTask;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.adapter.MemberMoreAdapter;
import com.example.administrator.jipinshop.adapter.MemberShopAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.PayResultBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.bean.eventbus.PayBus;
import com.example.administrator.jipinshop.databinding.FragmentMemberNewBinding;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.anim.AnimationUtils;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.MemberBuyPop;
import com.example.administrator.jipinshop.view.dialog.MemberRenewPop;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
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
public class MemberFragment extends DBBaseFragment implements View.OnClickListener, OnRefreshListener, MemberView, MemberBuyPop.OnClick, MemberRenewPop.OnClick {

    @Inject
    MemberPresenter mPresenter;
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
    private List<MemberNewBean.DataBean.VipBoxListBean> monthBoxList;
    private List<MemberNewBean.DataBean.VipBoxListBean> yearBoxList;
    private String preMonthEndTime = "";
    private String preYearEndTime = "";
    private IWXAPI msgApi;//微信支付
    private String level;//1是月卡 2是年卡
    private Dialog mDialog;
    private String endTime = "";//付款后的会员到期时间
    //广告
    private List<MemberNewBean.DataBean.MessageListBean> mAdList;
    //更多权益
    private List<MemberNewBean.DataBean.VipBoxListBean> mMoreList;
    private MemberMoreAdapter mMoreAdapter;
    //商品列表
    private List<MemberNewBean.DataBean.OrderLevelDataBean.OrderListBean> mOrderList;
    private MemberShopAdapter mShopAdapter;
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
                DialogUtil.payFileDialog(getContext(), type -> {
                    onBuyMember(level,type);
                });
            }
        }
        return true;
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());

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
        //更多权益
        mMoreList = new ArrayList<>();
        mMoreAdapter = new MemberMoreAdapter(mMoreList,getContext(),"1");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        mBinding.memberMore.setLayoutManager(linearLayoutManager);
        mBinding.memberMore.setNestedScrollingEnabled(false);
        mBinding.memberMore.setAdapter(mMoreAdapter);
        mBinding.memberMore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //划出去的宽度
                int isResult = getResult(linearLayoutManager);
                //可划出去的总宽度
                double totleWith = getTotleWith(linearLayoutManager);
                //线条可划出去的总宽度
                double lineWith = getContext().getResources().getDimension(R.dimen.x60);
                //结果
                double result  = (lineWith / totleWith) * isResult;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.memberPoint.getLayoutParams();
                layoutParams.leftMargin = (int) result;
                mBinding.memberPoint.setLayoutParams(layoutParams);
            }
        });
        //商品列表
        mOrderList = new ArrayList<>();
        mShopAdapter = new MemberShopAdapter(mOrderList,getContext());
        mBinding.memberShopList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.memberShopList.setNestedScrollingEnabled(false);
        mBinding.memberShopList.setAdapter(mShopAdapter);

        if (type.equals("2")){
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
                // TODO: 2020/8/27 规则说明
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
                AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_SHOW,100);
                mBuyPop.show(mBinding.memberPayContainer,"1",monthPrice,monthPriceBefore);
                break;
            case R.id.member_year:
                //购买年卡
                if (TextUtils.isEmpty(yearPrice) || TextUtils.isEmpty(yearPriceBefore)){
                    ToastUtil.show("网络错误，请稍后尝试");
                    return;
                }
                AnimationUtils.showAndHiddenAnimation(mBinding.memberShadow, AnimationUtils.AnimationState.STATE_SHOW,100);
                mBuyPop.show(mBinding.memberPayContainer,"2",yearPrice,yearPriceBefore);
                break;
            case R.id.member_rule:
                // TODO: 2020/8/27 会员规则
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

    private int getResult(LinearLayoutManager linearLayoutManager){
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        //获取Item的宽
        int itemWidth = 0;
        //算出该Item还未移出屏幕的高度
        int itemRight = 0;
        if (firstVisiableChildView != null){
            itemWidth = firstVisiableChildView.getWidth();
            itemRight = firstVisiableChildView.getRight();
        }
        //position移出屏幕的数量*高度得出移动的距离
        int iposition = position * itemWidth;
        //因为横着的RecyclerV第一个取到的Item position为零所以计算时需要加一个宽
        int iResult = iposition - itemRight + itemWidth;
        return  iResult;
    }

    private double getTotleWith(LinearLayoutManager linearLayoutManager){
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        //获取Item的宽
        int itemWidth = 0;
        if (firstVisiableChildView != null){
            itemWidth = firstVisiableChildView.getWidth();
        }
        double zWidth = (DistanceHelper.getAndroiodScreenwidthPixels(getContext()) - getContext().getResources().getDimension(R.dimen.x20) - getContext().getResources().getDimension(R.dimen.x20));
        return ((itemWidth * mMoreList.size()) - zWidth);
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

        mBinding.setBean(bean.getData());
        mBinding.executePendingBindings();
        mBinding.memberMonthOtherCost.setTv(true);
        mBinding.memberMonthOtherCost.setColor(R.color.color_white);
        mBinding.memberYearOtherCost.setTv(true);
        mBinding.memberYearOtherCost.setColor(R.color.color_white);
        //广告
        mAdList.clear();
        mAdList.addAll(bean.getData().getMessageList());
        mPresenter.adFlipper(getContext(),mBinding.viewFlipper,mAdList);
        //更多权益
        mMoreList.clear();
        mMoreList.addAll(bean.getData().getVipBoxList());
        if (mMoreList.size() > 3){
            mBinding.memberPointContainer.setVisibility(View.VISIBLE);
        }else{
            mBinding.memberPointContainer.setVisibility(View.GONE);
        }
        mMoreAdapter.notifyDataSetChanged();
        //商品列表
        mOrderList.clear();
        if (bean.getData().getOrderLevelData() != null){
            mOrderList.addAll(bean.getData().getOrderLevelData().getOrderList());
        }
        mShopAdapter.notifyDataSetChanged();
        //身份处理  0 普通 ， 1 月卡 ，2年卡
        if (bean.getData().getLevel() == 0){
            mBinding.memberPayContainer.setVisibility(View.VISIBLE);
            mBinding.memberAdContainer.setVisibility(View.VISIBLE);
            mBinding.memberMemberContainer.setVisibility(View.GONE);
            mBinding.memberTitle1.setText("开通VIP可享更多权益");
            mBinding.memberMonthTitle.setText("每月");
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
            mBinding.memberMonthTitle.setText("补贴余额");
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
                    DialogUtil.payFileDialog(getContext(), type -> {
                        onBuyMember(level, type);
                    });
                }
            }
        }
    }
}
