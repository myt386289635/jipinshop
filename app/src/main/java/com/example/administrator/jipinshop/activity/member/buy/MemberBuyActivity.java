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
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
import com.example.administrator.jipinshop.bean.PayResultBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.bean.eventbus.PayBus;
import com.example.administrator.jipinshop.databinding.ActivityMemberBuyBinding;
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
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/12/7
 * @Describe 会员购买和续费页面
 */
public class MemberBuyActivity extends BaseActivity implements View.OnClickListener, MemberBuyView {

    @Inject
    MemberBuyPresenter mPresenter;

    private ActivityMemberBuyBinding mBinding;
    private String level = "1";//1是月卡 2是年卡 3是周卡
    private String isBuy = "1"; //1是购买  2续费
    private CountDownTimer countDownTimer;//倒计时
    private Boolean startPop = true;//是否弹出关闭确认弹窗
    private String monthPrice = "";//月卡价格统计时候需要
    private String yearPrice = "";//年卡价格统计时候需要
    private int userLevel = 0;//用户身份的
    private Dialog mDialog;
    private IWXAPI msgApi;//微信支付
    private MemberBuyBean mBean = null;
    private String wx = "";//客服
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
                DialogUtil.payFileDialog(this,userLevel, type -> {
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
            level = getIntent().getStringExtra("level");
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
            case R.id.month_container:
                //月卡
                mBinding.monthCheckBox.setChecked(true);
                break;
            case R.id.year_container:
                //年卡
                mBinding.yearCheckBox.setChecked(true);
                break;
            case R.id.week_container:
                //周卡
                mBinding.weekCheckBox.setChecked(true);
                break;
            case R.id.month_other_container:
                //月卡
                mBinding.monthOtherCheckBox.setChecked(true);
                break;
            case R.id.year_other_container:
                //年卡
                mBinding.yearOtherCheckBox.setChecked(true);
                break;
            case R.id.buy_buy:
                //购买
                if (mBinding.buyAlipay.isChecked()){
                    onBuyMember(level,"1");
                }else {
                    onBuyMember(level,"2");
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
        if (level.equals("2")){
            mBinding.yearOtherContainer2.setVisibility(View.VISIBLE);
            mBinding.buyType2Container.setVisibility(View.GONE);
            mBinding.buyType1Container.setVisibility(View.GONE);
            mBinding.setYear(bean.getData().get(1));
        }else if (bean.getData().size() == 2){
            //没有周卡
            mBinding.yearOtherContainer2.setVisibility(View.GONE);
            mBinding.buyType2Container.setVisibility(View.VISIBLE);
            mBinding.buyType1Container.setVisibility(View.GONE);
            mBinding.setMoney(bean.getData().get(0));
            mBinding.setYear(bean.getData().get(1));
        }else {
            //有周卡
            mBinding.yearOtherContainer2.setVisibility(View.GONE);
            mBinding.buyType2Container.setVisibility(View.GONE);
            mBinding.buyType1Container.setVisibility(View.VISIBLE);
            mBinding.setMoney(bean.getData().get(0));
            mBinding.setYear(bean.getData().get(1));
            mBinding.setWeek(bean.getData().get(2));
        }
        initText();
        mBinding.buyPhone.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
        mBinding.monthOther.setTv(true);
        mBinding.monthOther.setColor(R.color.color_9D9D9D);
        mBinding.monthOtherOther.setTv(true);
        mBinding.monthOtherOther.setColor(R.color.color_9D9D9D);
        mBinding.yearOther.setTv(true);
        mBinding.yearOther.setColor(R.color.color_9D9D9D);
        mBinding.yearOtherOther.setTv(true);
        mBinding.yearOtherOther.setColor(R.color.color_9D9D9D);
    }

    public void initText(){//1是月卡 2是年卡 3是周卡
        if (mBean == null){
            return;
        }
        String html = "";
        if (level.equals("1")){
            if (isBuy.equals("1")){//购买
                mBinding.buyNoticeTag.setText("会员专享");
                html = "加入极品城会员，每年可省<font color='#E25838'>24000元</font>";
            }else {//续费
                mBinding.buyNoticeTag.setText("会员续费");
                html = "续费成功后会员延续至<font color='#E25838'>"+mBean.getData().get(0).getPreLevelEndTime()+"</font>";
            }
            mBinding.buyNotice.setText(mBean.getData().get(0).getRemark3());
            mBinding.buyNoticeTimeOne.setText(Html.fromHtml(html));
            GlideApp.loderImage(this,mBean.getData().get(0).getImg(),mBinding.buyNoticeImage,0,0);
            mBinding.buyMoney.setText(mBean.getData().get(0).getPrice());
            double price = new BigDecimal(mBean.getData().get(0).getPrice()).doubleValue();
            double priceBefore = new BigDecimal(mBean.getData().get(0).getPriceBefore()).doubleValue();
            String money = new BigDecimal((priceBefore - price) + "").stripTrailingZeros().toPlainString();
            mBinding.buyCheapMoney.setText("已优惠"+ money +"元");
        }else if (level.equals("2")){
            if (isBuy.equals("1")){//购买
                mBinding.buyNoticeTag.setText("会员专享");
                html = "加入极品城会员，每年可省<font color='#E25838'>24000元</font>";
            }else {//续费
                mBinding.buyNoticeTag.setText("会员续费");
                html = "续费成功后会员延续至<font color='#E25838'>"+mBean.getData().get(1).getPreYearLevelEndTime()+"</font>";
            }
            mBinding.buyNotice.setText(mBean.getData().get(1).getRemark3());
            mBinding.buyNoticeTimeOne.setText(Html.fromHtml(html));
            GlideApp.loderImage(this,mBean.getData().get(1).getImg(),mBinding.buyNoticeImage,0,0);
            mBinding.buyMoney.setText(mBean.getData().get(1).getPrice());
            double price = new BigDecimal(mBean.getData().get(1).getPrice()).doubleValue();
            double priceBefore = new BigDecimal(mBean.getData().get(1).getPriceBefore()).doubleValue();
            String money = new BigDecimal((priceBefore - price) + "").stripTrailingZeros().toPlainString();
            mBinding.buyCheapMoney.setText("已优惠"+ money +"元");
        }else if (level.equals("3")){
            if (isBuy.equals("1")){//购买
                mBinding.buyNoticeTag.setText("会员专享");
                html = "加入极品城会员，每年可省<font color='#E25838'>24000元</font>";
            }else {//续费
                mBinding.buyNoticeTag.setText("会员续费");
                html = "续费成功后会员延续至<font color='#E25838'>"+mBean.getData().get(2).getPreLevelEndTime()+"</font>";
            }
            mBinding.buyNotice.setText(mBean.getData().get(2).getRemark3());
            mBinding.buyNoticeTimeOne.setText(Html.fromHtml(html));
            GlideApp.loderImage(this,mBean.getData().get(2).getImg(),mBinding.buyNoticeImage,0,0);
            mBinding.buyMoney.setText(mBean.getData().get(2).getPrice());
            mBinding.buyCheapMoney.setText("");
        }
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

    @Override
    public void onCommenFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    //初始化文案
    @Override
    public void onInit(String userLevel) {
        this.level = userLevel;
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
                DialogUtil.payFileDialog(this,userLevel,  type -> {
                    onBuyMember(level, type);
                });
            }
        }
    }
}
