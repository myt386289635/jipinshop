package com.example.administrator.jipinshop.activity.info.account;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhone2Activity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhoneActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.ActivityAccountBinding;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/21
 * @Describe 账户管理
 */
public class AccountManageActivity extends BaseActivity implements View.OnClickListener, AccountManageView {

    public static final String tag = "AccountWX";

    @Inject
    AccountManagePersenter mPersenter;
    private ActivityAccountBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_account);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPersenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("账号管理");
        if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindWeixin,"0").equals("1")){
            //已经绑定微信了
            mBinding.changeWx.setBackgroundResource(R.drawable.bg_account);
            mBinding.changeWx.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.changeWx.setText("已绑定");
            mBinding.boundWx.setImageResource(R.mipmap.wechat_sel);
        }else {
            mBinding.changeWx.setBackgroundResource(R.drawable.bg_login2);
            mBinding.changeWx.setTextColor(getResources().getColor(R.color.color_white));
            mBinding.changeWx.setText("去绑定");
            mBinding.boundWx.setImageResource(R.mipmap.wechat_nor);
        }
        mBinding.boundPhoneNum.setText(FileManager.editPhone(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.change_phone:
                //跳转到更换手机号页面
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.change_wx:
                if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindWeixin,"0").equals("1")){
                    //已绑定
                }else {
                    //去绑定
                    authorization(SHARE_MEDIA.WEIXIN);
                }
                break;
        }
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        final Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(AccountManageActivity.this, "");
        UMShareAPI umShareAPI = UMShareAPI.get(this);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);//授权页面是吊起来了，但是由于有缓存会自动授权
        umShareAPI.setShareConfig(config);
        umShareAPI.getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if(mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
                String uid = map.get("uid");
                String openid = map.get("openid");
                String access_token = map.get("access_token");
                if(share_media == SHARE_MEDIA.WEIXIN){
                    //绑定微信
                    mPersenter.bindThirdAccount(access_token,openid,"1",AccountManageActivity.this.bindToLifecycle());
                }else {
                    ToastUtil.show("第三方绑定跳转失败，请联系管理员");
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("login", "onError " + "授权失败----" + i +"----" +throwable.getMessage());
                if(mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
                //授权失败后取消自动授权
                UMShareAPI.get(AccountManageActivity.this).deleteOauth(AccountManageActivity.this, share_media, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d("login", "onCancel " + "授权取消");
                if(mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onSuccess(SuccessBean bean) {
        if(bean.getCode() == 0){
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin,"1");
            ToastUtil.show("微信绑定成功");
            mBinding.changeWx.setBackgroundResource(R.drawable.bg_account);
            mBinding.changeWx.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.changeWx.setText("已绑定");
            mBinding.boundWx.setImageResource(R.mipmap.wechat_sel);
            EventBus.getDefault().post(new EditNameBus(AccountManageActivity.tag));
        }
    }


    @Subscribe
    public void eidtInfo(EditNameBus bus){
        if(bus.getTag().equals(ChangePhone2Activity.tag)){
            mBinding.boundPhoneNum.setText(FileManager.editPhone(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone)));
        }
    }
}
