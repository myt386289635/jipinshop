package com.example.administrator.jipinshop.activity.balance.withdraw;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityWithdrawBinding;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe 我要提现
 */
public class WithdrawActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    WithdrawPresenter mPresenter;
    private ActivityWithdrawBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_withdraw);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("我要提现");
        String html = "温馨提示：最低提现金额为<font color='#E31436'>50元</font>";
        mBinding.withdrawNodeMoney.setText(Html.fromHtml(html));
        mPresenter.initMoneyEdit(mBinding);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.withdraw_withdraw:

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
}
