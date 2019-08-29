package com.example.administrator.jipinshop.activity.balance.withdraw;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;
import com.example.administrator.jipinshop.bean.eventbus.WithdrawBus;
import com.example.administrator.jipinshop.databinding.ActivityWithdrawBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe 我要提现
 */
public class WithdrawActivity extends BaseActivity implements View.OnClickListener, WithdrawView {

    @Inject
    WithdrawPresenter mPresenter;
    private ActivityWithdrawBinding mBinding;
    private double minWithdraw = 50;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_withdraw);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.taobaoAccount(this.bindToLifecycle());
        mPresenter.getWithdrawNote(this.bindToLifecycle());
        mBinding.inClude.titleTv.setText("我要提现");
        mPresenter.initMoneyEdit(mBinding);
        mBinding.withdrawMoney.setText(getIntent().getStringExtra("price"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.withdraw_withdraw:
                if(TextUtils.isEmpty(mBinding.withdrawName.getText().toString())){
                    ToastUtil.show("请输入真实姓名");
                    return;
                }
                if(TextUtils.isEmpty(mBinding.withdrawNumber.getText().toString())){
                    ToastUtil.show("请输入支付宝账号");
                    return;
                }
                if(TextUtils.isEmpty(mBinding.withdrawPay.getText().toString())){
                    ToastUtil.show("请输入提现金额");
                    return;
                }
                BigDecimal bigDecimal = new BigDecimal(mBinding.withdrawPay.getText().toString());
                double money =  bigDecimal.doubleValue();
                if(money < minWithdraw){
                    ToastUtil.show("最低提款金额为"+minWithdraw+"元");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.withdraw(mBinding.withdrawName.getText().toString(),
                        mBinding.withdrawNumber.getText().toString(),
                        mBinding.withdrawPay.getText().toString(),
                        this.bindToLifecycle());
                break;
            case R.id.withdraw_payTotle:
                mBinding.withdrawPay.setText(mBinding.withdrawMoney.getText().toString()
                        .replace("¥","")
                );
                mBinding.withdrawPay.setSelection(mBinding.withdrawPay.getText().toString().length());
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                showKeyboard(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSuccess(WithdrawBean bean) {
        minWithdraw = new BigDecimal(bean.getMinWithdraw()).doubleValue();
        String html = "温馨提示：最低提现金额为<font color='#E25838'>"+ bean.getMinWithdraw() + "元</font>";
        mBinding.withdrawNodeMoney.setText(Html.fromHtml(html));
        mBinding.withdrawMode.setText(bean.getWithdrawNote());
    }

    @Override
    public void onFile(String error) {
        ToastUtil.show(error);
        String html = "温馨提示：最低提现金额为<font color='#E25838'>"+minWithdraw+"元</font>";
        mBinding.withdrawNodeMoney.setText(Html.fromHtml(html));
    }

    @Override
    public void onWithdrawSuccess() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show("提现成功");
        EventBus.getDefault().post(new WithdrawBus(mBinding.withdrawPay.getText().toString()));
        finish();
    }

    @Override
    public void onWithdrawFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSuccessAccount(TaobaoAccountBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (bean.getData() != null && !TextUtils.isEmpty(bean.getData().getAccount()) && !TextUtils.isEmpty(bean.getData().getRealname())){
            mBinding.withdrawNameText.setVisibility(View.GONE);
            mBinding.withdrawName.setText(bean.getData().getRealname());
            mBinding.withdrawNumber.setText(bean.getData().getAccount());
            mBinding.withdrawNumberText.setVisibility(View.GONE);
        }
    }
}
