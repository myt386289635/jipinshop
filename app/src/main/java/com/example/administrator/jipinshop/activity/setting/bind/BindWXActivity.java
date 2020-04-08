package com.example.administrator.jipinshop.activity.setting.bind;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityBindWxBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/4/7
 * @Describe 绑定微信
 */
public class BindWXActivity extends BaseActivity implements View.OnClickListener, BindWXView {

    @Inject
    BindWXPresenter mPresenter;

    private ActivityBindWxBinding mBinding;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_bind_wx);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.inClude.titleTv.setText("修改微信号");
        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.wechat, ""))){
            mBinding.bindEdit.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.wechat, ""));
        }else {
            mBinding.bindEdit.setText("");
        }
        mBinding.bindEdit.setSelection(mBinding.bindEdit.getText().length());//将光标移至文字末尾
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                setResult(400);
                finish();
                break;
            case R.id.bind_sure:
                if (TextUtils.isEmpty(mBinding.bindEdit.getText().toString().trim())){
                    ToastUtil.show("请输入微信号");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                mDialog.show();
                mPresenter.SaveUserInfo(mBinding.bindEdit.getText().toString().trim(),this.bindToLifecycle());
                break;
        }
    }

    @Override
    public void onSuccess() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        setResult(200);
        finish();
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.wechat,mBinding.bindEdit.getText().toString().trim());
        ToastUtil.show("修改成功");
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onBackPressed() {
        setResult(400);
        super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hideSoftInput(view);
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

    private void hideSoftInput(View view) {
        if (mImm.isActive()) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }
}
