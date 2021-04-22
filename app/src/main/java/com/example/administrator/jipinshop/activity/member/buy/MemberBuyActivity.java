package com.example.administrator.jipinshop.activity.member.buy;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.adapter.MemberBuyAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
import com.example.administrator.jipinshop.bean.PayResultBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.bean.eventbus.PayBus;
import com.example.administrator.jipinshop.databinding.ActivityMemberBuyBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.TimeUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.StatisticalUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/12/7
 * @Describe 会员购买和续费页面
 */
public class MemberBuyActivity extends BaseActivity implements View.OnClickListener, MemberBuyView, MemberBuyAdapter.OnItem {

    @Inject
    MemberBuyPresenter mPresenter;

    private ActivityMemberBuyBinding mBinding;
    private String level = "1";//购买时选择的哪种卡  1是月卡 2是年卡 3是周卡  4是包月  5是包年
    private String isBuy = "1"; //1是购买  2续费
    private CountDownTimer countDownTimer;//倒计时
    private Boolean startPop = true;//是否弹出关闭确认弹窗
    private String monthPrice = "";//月卡价格统计时候需要
    private String yearPrice = "";//年卡价格统计时候需要
    private Dialog mDialog;
    private IWXAPI msgApi;//微信支付
    private MemberBuyBean mBean = null;
    private String wx = "";//客服
    private MemberBuyAdapter mAdapter;
    private List<MemberBuyBean.DataBean> mList;
    private int set = 0;//选中的是哪个位置  默认选中的是0
    //支付宝支付回调
    private Handler.Callback mCallback = msg -> {
        if (msg.what == 101){
            //支付宝支付回调
            PayResultBean payResult = new PayResultBean((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                //成功
                if (level.equals("1")){
                    StatisticalUtil.onPayEvent(this,"月卡", monthPrice);
                }else  if (level.equals("2")){
                    StatisticalUtil.onPayEvent(this,"年卡", yearPrice);
                }
                Intent intent = new Intent();
                intent.putExtra("level",level);
                setResult(200,intent);
                finish();
            } else {
                //失败
                DialogUtil.payFileDialog(this,isBuy,"1", type -> {
                    onBuyMember(level,type);
                });
            }
        }
        return true;
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_member_buy);
        mBinding.setListener(this);
        EventBus.getDefault().register(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        isBuy = getIntent().getStringExtra("isBuy");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("level"))){
            level = getIntent().getStringExtra("level");//打开时是否需要单独显示为年卡
        }
        mBinding.inClude.titleTv.setText("确认订单");
        countDownTimer = new CountDownTimer(15 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String html = "请在<b><font color='#E25838'>"+TimeUtil.getMinuteByLong(millisUntilFinished) +
                        "</font></b>分<b><font color='#E25838'>" + TimeUtil.getSecondByLong(millisUntilFinished) +
                        "</font></b>秒内完成支付";
                mBinding.buyCountTime.setText(Html.fromHtml(html));
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        }.start();
        mPresenter.initCheckBox(this,mBinding);

        //初始化微信支付
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp("wxfd2e92db2568030a");

        mBinding.buyChoose.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mList = new ArrayList<>();
        mAdapter = new MemberBuyAdapter(mList,this);
        mAdapter.setSet(set);
        mAdapter.setOnItem(this);
        mBinding.buyChoose.setAdapter(mAdapter);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.listVipList(this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                twoFinishPage();
                break;
            case R.id.buy_official:
                DialogUtil.LoginDialog(this, "官方客服微信：" + wx, "复制", "取消", v1 -> {
                    ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("jipinshop", wx);
                    clip.setPrimaryClip(clipData);
                    ToastUtil.show("微信号复制成功");
                    SPUtils.getInstance().put(CommonDate.CLIP, wx);
                });
                break;
            case R.id.buy_buy:
                //购买
                if (level.equals("3")){
                    //支付周卡
                    if (mBean == null){
                        ToastUtil.show("正在请求数据，请稍等");
                        return;
                    }
                    double point = new BigDecimal(mList.get(set).getPrice()).doubleValue();
                    if (mBean.getPoint() >= point){
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                        mDialog.show();
                        mPresenter.pointPay(3,this.bindToLifecycle());
                    }else {
                        startActivity(new Intent(this, SignActivity.class));
                    }
                }else {
                    if (level.equals("4") || level.equals("5")){
                       if (!mBinding.buyAlipay.isChecked()){
                           ToastUtil.show("请选择支付方式");
                           return;
                       }
                    }
                    if (mBinding.buyAlipay.isChecked()){
                        onBuyMember(level,"1");
                    }else {
                        onBuyMember(level,"2");
                    }
                }
                break;
            case R.id.member_rule:
                if (mList.get(set).getLevel() == 4 || mList.get(set).getLevel() == 5){
                    startActivity(new Intent(this, WebActivity.class)
                            .putExtra(WebActivity.url, RetrofitModule.JP_H5_URL + "new-free/mAutoRenew")
                            .putExtra(WebActivity.title,"会员连续包月自动续费协议")
                    );
                }else {
                    startActivity(new Intent(this, WebActivity.class)
                            .putExtra(WebActivity.url, RetrofitModule.JP_H5_URL + "new-free/memberAgreement")
                            .putExtra(WebActivity.title,"会员服务协议")
                    );
                }
                break;
        }
    }

    @Override
    public void onSuccess(MemberBuyBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBean = bean;
        monthPrice = bean.getData().get(0).getPrice();
        yearPrice = bean.getData().get(1).getPrice();
        wx = bean.getOfficialWechat();
        mBinding.buyPhone.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
        //显示数据
        set = 0;
        mList.clear();
        mList.addAll(bean.getData());
        mAdapter.setSet(set);
        mAdapter.notifyDataSetChanged();
        level = mList.get(set).getLevel() + "";//购买的哪个卡
        initText();
    }

    public void initText(){//1是月卡 2是年卡 3是周卡
        String html = "";
        if (isBuy.equals("1")){//购买
            mBinding.buyNoticeTag.setText("会员专享");
            html = "加入极品城会员，每年可省<font color='#E25838'>24000元</font>";
        }else {//续费
            mBinding.buyNoticeTag.setText("会员续费");
            html = "续费成功后会员延续至<font color='#E25838'>"+mList.get(set).getPreLevelEndTime()+"</font>";
        }
        mBinding.buyNoticeTimeOne.setText(Html.fromHtml(html));
        mBinding.buyNotice.setText(mList.get(set).getRemark3());
        GlideApp.loderImage(this,mList.get(set).getImg(),mBinding.buyNoticeImage,0,0);
        mBinding.buyMoney.setText(mList.get(set).getPrice());
        double price = new BigDecimal(mList.get(set).getPrice()).doubleValue();
        double priceBefore = new BigDecimal(mList.get(set).getPriceBefore()).doubleValue();
        String money = new BigDecimal((priceBefore - price) + "").stripTrailingZeros().toPlainString();
        String rule = "";
        if (mList.get(set).getLevel() == 3){//选中周卡
            mBinding.buyCheapMoney.setText("已优惠"+ money +"极币");
            mBinding.buyContainer.setVisibility(View.GONE);
            mBinding.buyTag.setText("极币");
            double point = new BigDecimal(mList.get(set).getPrice()).doubleValue();
            if (mBean.getPoint() >= point){
                mBinding.buyBuy.setText("确认兑换");
            }else {
                mBinding.buyBuy.setText("极币不足\n去赚取");
            }
            rule = "开通会员代表接受<font color='#3E85FB'>《会员服务协议》</font>";
        }else {//非周卡
            mBinding.buyCheapMoney.setText("已优惠"+ money +"元");
            mBinding.buyContainer.setVisibility(View.VISIBLE);
            mBinding.buyTag.setText("￥");
            mBinding.buyBuy.setText("确认支付");
            if (mList.get(set).getLevel() == 4 || mList.get(set).getLevel() == 5){
                rule = "开通会员代表接受<font color='#3E85FB'>《会员连续包月自动续费协议》</font>";
            }else {
                rule = "开通会员代表接受<font color='#3E85FB'>《会员服务协议》</font>";
            }
            if (mList.get(set).getLevel() == 4 || mList.get(set).getLevel() == 5){
                mBinding.buyWxName.setVisibility(View.GONE);
                mBinding.buyWxpay.setVisibility(View.GONE);
            }else {
                mBinding.buyWxName.setVisibility(View.VISIBLE);
                mBinding.buyWxpay.setVisibility(View.VISIBLE);
            }
        }
        mBinding.memberRule.setText(Html.fromHtml(rule));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        twoFinishPage();
    }

    //退出页面
    public void twoFinishPage(){
//        if (startPop){
//            DialogUtil.buyOutDialog(this, v1 -> {
//                finish();
//            });
//            startPop = false;
//        }else {
            finish();
//        }
    }

    //购买
    // level 1是月卡 2是年卡 3是周卡    //type 1是支付宝 2是微信
    public void onBuyMember(String level, String type) {
        this.level = level;
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
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
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(this);
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

    //周卡支付结果
    @Override
    public void onPoint() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        Intent intent = new Intent();
        intent.putExtra("level",level);
        setResult(200,intent);
        finish();
    }

    @Override
    public void onCommenFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    //初始化文案
    @Override
    public void onItem(int position) {
        set = position;
        mAdapter.setSet(set);
        mAdapter.notifyDataSetChanged();
        level = mList.get(position).getLevel() + "";//购买的哪个卡
        initText();
    }

    @Subscribe
    public void onPayResult(PayBus bus){
        if (bus != null){
            if (bus.getType().equals(WXPayEntryActivity.pay_success)) {
                if (level.equals("1")){
                    StatisticalUtil.onPayEvent(this,"月卡", monthPrice);
                }else  if (level.equals("2")){
                    StatisticalUtil.onPayEvent(this,"年卡", yearPrice);
                }
                Intent intent = new Intent();
                intent.putExtra("level",level);
                setResult(200,intent);
                finish();
            } else if (bus.getType().equals(WXPayEntryActivity.pay_faile)) {
                DialogUtil.payFileDialog(this,isBuy,"2",  type -> {
                    onBuyMember(level, type);
                });
            }
        }
    }
}
