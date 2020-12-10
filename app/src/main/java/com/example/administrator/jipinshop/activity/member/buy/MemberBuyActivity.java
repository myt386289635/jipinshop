package com.example.administrator.jipinshop.activity.member.buy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityMemberBuyBinding;
import com.example.administrator.jipinshop.util.TimeUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/12/7
 * @Describe 会员购买和续费页面
 */
public class MemberBuyActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    MemberBuyPresenter mPresenter;

    private ActivityMemberBuyBinding mBinding;
    private String level = "1";//1是月卡 2是年卡 3是周卡
    private String type = "1";//1是支付宝  2是微信
    private String isBuy = "1"; //1是购买  2续费
    private CountDownTimer countDownTimer;//倒计时
    private Boolean startPop = true;//是否弹出关闭确认弹窗

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_member_buy);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        isBuy = getIntent().getStringExtra("isBuy");
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                if (startPop){
                    DialogUtil.buyOutDialog(this, v1 -> {
                        finish();
                    });
                    startPop = false;
                }else {
                    finish();
                }
                break;
            case R.id.buy_official:
                ToastUtil.show("咨询客服");
                break;
            case R.id.month_container:
                //月卡
                mBinding.monthCheckBox.setChecked(true);
                mBinding.yearCheckBox.setChecked(false);
                mBinding.weekCheckBox.setChecked(false);
                level = "1";
                break;
            case R.id.year_container:
                //年卡
                mBinding.monthCheckBox.setChecked(false);
                mBinding.yearCheckBox.setChecked(true);
                mBinding.weekCheckBox.setChecked(false);
                level = "2";
                break;
            case R.id.week_container:
                //周卡
                mBinding.monthCheckBox.setChecked(false);
                mBinding.yearCheckBox.setChecked(false);
                mBinding.weekCheckBox.setChecked(true);
                level = "3";
                break;
            case R.id.month_other_container:
                //月卡
                mBinding.monthOtherCheckBox.setChecked(true);
                level = "1";
                break;
            case R.id.year_other_container:
                //年卡
                mBinding.yearOtherCheckBox.setChecked(true);
                level = "2";
                break;
            case R.id.buy_buy:
                //购买
                String buy = "";
                if (level.equals("1")){
                    buy = "月卡";
                }else if (level.equals("2")){
                    buy = "年卡";
                }else {
                    buy = "周卡";
                }
                if (mBinding.buyAlipay.isChecked()){
                    buy = buy + "----支付宝";
                }else {
                    buy = buy + "----微信";
                }
                ToastUtil.show(buy);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (startPop){
            DialogUtil.buyOutDialog(this, v1 -> {
                super.onBackPressed();
            });
            startPop = false;
        }else {
            super.onBackPressed();
        }
    }

}
