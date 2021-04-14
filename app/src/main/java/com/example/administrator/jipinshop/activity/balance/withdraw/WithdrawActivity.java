package com.example.administrator.jipinshop.activity.balance.withdraw;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.balance.withdraw.detail.WithdrawDetailActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;
import com.example.administrator.jipinshop.bean.eventbus.WithdrawBus;
import com.example.administrator.jipinshop.databinding.ActivityWithdrawBinding;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog4;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe 我要提现
 */
public class WithdrawActivity extends BaseActivity implements View.OnClickListener, WithdrawView, ShareBoardDialog4.onShareListener {

    @Inject
    WithdrawPresenter mPresenter;
    private ActivityWithdrawBinding mBinding;
    private double minWithdraw = 5;
    private Dialog mDialog;
    private String officialWeChat = "";//客服
    private ShareBoardDialog4 mShareBoardDialog;

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
        mBinding.inClude.titleTv.setText("我要提现");
        mPresenter.initMoneyEdit(mBinding);
        mBinding.withdrawMoney.setText(getIntent().getStringExtra("price"));

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.taobaoAccount(this.bindToLifecycle());
        mPresenter.getWithdrawNote(this.bindToLifecycle());//请求提示语
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
            case R.id.withdraw_title:
                //提现明细
                startActivity(new Intent(this, WithdrawDetailActivity.class));
                break;
            case R.id.mine_server:
                DialogUtil.LoginDialog(this, "官方客服微信：" + officialWeChat, "复制", "取消", v1 -> {
                    ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("jipinshop", officialWeChat);
                    clip.setPrimaryClip(clipData);
                    ToastUtil.show("微信号复制成功");
                    SPUtils.getInstance().put(CommonDate.CLIP, officialWeChat);
                });
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
        mBinding.withdrawMode.setText(bean.getWithdrawNote());
    }

    @Override
    public void onFile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onWithdrawSuccess(WithdrawBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        EventBus.getDefault().post(new WithdrawBus(mBinding.withdrawPay.getText().toString()));
        mBinding.withdrawMoney.setText(bean.getData());
        DialogUtil.withdrawDialog(this, mBinding.withdrawPay.getText().toString(),
                bean.getPoint(), bean.getShareContent(), v -> {
                    if (mShareBoardDialog == null) {
                        mShareBoardDialog = ShareBoardDialog4.getInstance("批量存图");
                        mShareBoardDialog.setOnShareListener(this);
                    }
                    if (!mShareBoardDialog.isAdded()) {
                        mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog4");
                    }
        });
        mBinding.withdrawPay.setText("");
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
        officialWeChat = bean.getOfficialWechat();//客服
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initShare(SHARE_MEDIA share_media ,ShareInfoBean bean) {
        if (share_media != null){
            if (mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
            }
            new ShareUtils(this, share_media)
                    .shareImage(this, bean.getData().getImgUrl());
        }else {
            //保存图片
            Glide.with(this)
                    .asBitmap()
                    .load(bean.getData().getImgUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                            FileManager.saveFile(resource, WithdrawActivity.this);
                            ToastUtil.show("已保存到相册");
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.initShare(6,share_media,this.bindToLifecycle());
        mPresenter.taskFinish("26",this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    @Override
    public void download(int type) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.initShare(6,null,this.bindToLifecycle());
        mPresenter.taskFinish("26",this.bindUntilEvent(ActivityEvent.DESTROY));
    }
}
