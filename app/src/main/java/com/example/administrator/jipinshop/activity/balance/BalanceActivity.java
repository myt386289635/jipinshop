package com.example.administrator.jipinshop.activity.balance;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.balance.boundalipay.BoundAlipayActivity;
import com.example.administrator.jipinshop.activity.balance.record.RecordActivity;
import com.example.administrator.jipinshop.activity.balance.score.ScoreActivity;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.ActivityBalanceBinding;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 我的余额
 */
public class BalanceActivity extends RxAppCompatActivity implements View.OnClickListener, BalanceView {

    public static final String alipayTag = "BalanceActivity";//刷新佣金

    private ImmersionBar mImmersionBar;
    private ActivityBalanceBinding mBinding;

    @Inject
    BalancePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_balance);
        mBinding.setListener(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .build().inject(this);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        initView();
    }

    private void initView() {
        String html = "温馨提示：最低提现金额为<font color='#E31436'>50</font>元，24小时内到账" ;
        mBinding.balanceAction.setText(Html.fromHtml(html));
        mPresenter.setView(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,this);

//        mBinding.balanceWithdrawMoney.setText("00.00");
        mBinding.balanceMoney.setText(getIntent().getStringExtra("totleMoney"));
        mBinding.mineProcessingValue.setText(getIntent().getStringExtra("processingValue"));
        mBinding.mineWithdrawableValue.setText(getIntent().getStringExtra("withdrawableValue"));
        mBinding.mineSettlementValue.setText(getIntent().getStringExtra("settlementValue"));
        mBinding.mineWithdrawedValue.setText(getIntent().getStringExtra("withdrawedValue"));
        mBinding.balanceTotleMoney.setText("佣金¥"+getIntent().getStringExtra("withdrawableValue")+"，");
        if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipAccount).trim())){
            mBinding.balanceBoundText.setText("支付宝账号："+SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipAccount));
        }

        mPresenter.initMoneyEdit(mBinding.balanceWithdrawMoney,mBinding.balanceMoney.getText().toString());
    }

    @Override
    protected void onDestroy() {
        mImmersionBar.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_record:
                startActivity(new Intent(this, RecordActivity.class));
                break;
            case R.id.balance_score:
                startActivity(new Intent(this, ScoreActivity.class));
                break;
            case R.id.balance_boundAlipay:
                startActivityForResult(new Intent(this, BoundAlipayActivity.class),100);
                break;
            case R.id.balance_withdraw:
                //全部提现
                mBinding.balanceWithdrawMoney.setText(mBinding.balanceTotleMoney.getText().toString()
                        .replace("佣金¥","")
                        .replace("，","")
                        .replace(",",""));
                mBinding.balanceWithdrawMoney.setSelection(mBinding.balanceWithdrawMoney.getText().toString().length());
                break;
            case R.id.balance_withdrawButton:
                if(TextUtils.isEmpty(mBinding.balanceWithdrawMoney.getText().toString())){
                    Toast.makeText(this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mBinding.balanceBoundText.getText().toString().equals("请输入支付宝账号")){
                    Toast.makeText(this, "请输入支付宝账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal bigDecimal = new BigDecimal(mBinding.balanceWithdrawMoney.getText().toString());
                double money =  bigDecimal.doubleValue();
                if(money < 50){
                    Toast.makeText(this, "最低提款金额为50元", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.lipay(mDialog,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipAccount)
                        ,money + ""
                        ,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipName),this.bindToLifecycle());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 200:
                //绑定支付宝返回时
                mBinding.balanceBoundText.setText("支付宝账号："+SPUtils.getInstance(CommonDate.USER).getString(CommonDate.alipAccount,""));
                break;
        }
    }

    /**
     * 佣金刷新回调
     * @param accountBean
     */
    @Override
    public void successMoney(AccountBean accountBean) {
        if(accountBean.getCode() == 200){
            mBinding.balanceMoney.setText("总佣金¥" + accountBean.getList().get(0).getTotal_account());
            mBinding.mineProcessingValue.setText(accountBean.getList().get(0).getState());
            BigDecimal totleDecimal= new BigDecimal(accountBean.getList().get(0).getTotal_account());
            BigDecimal useDecimal = new BigDecimal("50");
            double value = totleDecimal.subtract(useDecimal).doubleValue();
            if(value >= 0){
                mBinding.mineWithdrawableValue.setText(accountBean.getList().get(0).getTotal_account());
            }else {
                mBinding.mineWithdrawableValue.setText("0");
            }
            mBinding.mineSettlementValue.setText("0");
            mBinding.mineWithdrawedValue.setText(accountBean.getList().get(0).getUse_account());
            mBinding.balanceTotleMoney.setText("佣金¥"+accountBean.getList().get(0).getTotal_account()+"，");
        }else {
            Toast.makeText(this, "佣金金额获取失败，请联系客服", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 支付宝提现回调
     * @param successBean
     */
    @Override
    public void alipySuccess(SuccessBean successBean) {
        if(successBean.getCode() == 200){
            //刷新佣金——我的佣金
            mPresenter.getMoney(this,this.bindToLifecycle());
            //刷新佣金——我的
            EventBus.getDefault().post(new EditNameBus(BalanceActivity.alipayTag));
            Toast.makeText(this, successBean.getMsg(), Toast.LENGTH_SHORT).show();
            mBinding.balanceWithdrawMoney.setText("");
        }else {
            Toast.makeText(this, successBean.getMsg() + ",请核对支付宝信息", Toast.LENGTH_SHORT).show();
        }
    }
}
