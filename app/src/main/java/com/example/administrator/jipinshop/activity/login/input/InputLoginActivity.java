package com.example.administrator.jipinshop.activity.login.input;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.login.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeRefresh;
import com.example.administrator.jipinshop.databinding.ActivityBindNumberBinding;
import com.example.administrator.jipinshop.jpush.JPushAlias;
import com.example.administrator.jipinshop.jpush.LoginUtil;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.tencent.captchasdk.TCaptchaDialog;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/5/13
 * @Describe 输入性登陆
 */
public class InputLoginActivity extends BaseActivity implements View.OnClickListener, InputLoginView {

    private ActivityBindNumberBinding mBinding;
    @Inject
    InputLoginPresenter mPresenter;
    @Inject
    AppStatisticalUtil appStatisticalUtil;

    private CountDownTimer mTimer;
    private Boolean[] timerStart = {false};
    private Dialog mDialog;
    private int newpeople = 0;//判断是否是从弹框点击来的  0 不是从弹框点击来的  1 是从新人弹框点击来的

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bind_number);
        mBinding.setListener(this);
        mBinding.loginWx.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.inClude.titleTv.setText("手机号登录");
        mBinding.inClude.titleLine.setVisibility(View.GONE);
        newpeople = getIntent().getIntExtra("newpeople",0);
        mBinding.loginButton.setEnabled(false);
        mTimer = mPresenter.initTimer(this, mBinding.loginGetCode);
        mPresenter.initLoginButton(mBinding.loginNumber, mBinding.loginCode, mBinding.loginButton);
        String content= "登录即代表同意《极品城用户协议》及《隐私政策》";
        SpannableString string = new SpannableString(content);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(InputLoginActivity.this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"privacy.html")
                        .putExtra(WebActivity.title,"隐私政策")
                );
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(getResources().getColor(R.color.color_4E89FF));
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(InputLoginActivity.this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"agreement.html")
                        .putExtra(WebActivity.title,"用户协议")
                );
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(getResources().getColor(R.color.color_4E89FF));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(clickableSpan2,7,16, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//用户协议
        string.setSpan(clickableSpan1,17,23, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//隐私政策
        // 设置此方法后，点击事件才能生效
        mBinding.loginProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        //去掉点击效果
        mBinding.loginProtocol.setHighlightColor(Color.TRANSPARENT);
        mBinding.loginProtocol.setText(string);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_getCode:
                if (timerStart[0]){
                    return;
                }
                if (mBinding.loginNumber.getText().toString().length() != 11) {
                    ToastUtil.show("手机号输入错误，请检查");
                    return;
                }
                if (RetrofitModule.needVerify.equals("1")){//是否需要安全验证（0不需要，1需要）
                    TCaptchaDialog dialog = new TCaptchaDialog(this, true, dialog1 ->{
                        ToastUtil.show("验证取消");
                    },"2087266956", jsonObject -> {
                        try {
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) { //验证成功
                                String ticket = jsonObject.getString("ticket");
                                String randstr = jsonObject.getString("randstr");
                                send(ticket,randstr);
                            } else if (ret == -1001) {// 首个TCaptcha.js加载错误时允许用户(操作者)或业务方(接入方)介入重试
                                ToastUtil.show("验证码加载错误：" + jsonObject.toString());
                            } else { //用户(可能)点了验证码的关闭按钮
                                ToastUtil.show("验证失败：" +  jsonObject.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },null);
                    dialog.show();
                }else {
                    send("","");
                }
                break;
            case R.id.login_button:
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                String invitationCode = "";
                mPresenter.login(mBinding.loginNumber.getText().toString(),mBinding.loginCode.getText().toString(),
                        invitationCode ,this.<LoginBean>bindToLifecycle());
                break;
            case R.id.login_wx:
                appStatisticalUtil.addEvent("login_weixin",this.bindUntilEvent(ActivityEvent.DESTROY));
                authorization(SHARE_MEDIA.WEIXIN);
                break;
        }
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        final Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(InputLoginActivity.this, "");
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
                    //微信
                    mPresenter.thirdLogin(access_token,openid,"1",InputLoginActivity.this.bindToLifecycle());
                }else {
                    ToastUtil.show("第三方登陆跳转失败，请联系管理员");
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("login", "onError " + "授权失败----" + i +"----" +throwable.getMessage());
                if(mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
                //授权失败后取消自动授权
                UMShareAPI.get(InputLoginActivity.this).deleteOauth(InputLoginActivity.this, share_media, new UMAuthListener() {
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
    public void timerEnd() {
        timerStart[0] = false;
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (loginBean.getCode() == 0){
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userAcutalName,loginBean.getData().getRealname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday,loginBean.getData().getBirthday());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender,loginBean.getData().getGender());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade,loginBean.getData().getMemberLevel());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg,loginBean.getData().getAvatar());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName,loginBean.getData().getNickname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone,loginBean.getData().getMobile());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.token,loginBean.getData().getToken());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint,loginBean.getData().getPoint());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindMobile, loginBean.getData().getBindMobile() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeibo, loginBean.getData().getBindWeibo() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin, loginBean.getData().getBindWeixin() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.qrCode, loginBean.getData().getInvitationCode());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userId,loginBean.getData().getUserId());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, loginBean.getData().getRelationId());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.isNewUser, loginBean.getData().getIsNewUser());

            JPushAlias.setAlias(this,loginBean.getData().getUserId());
            String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
            if (deviceBrand.equals("xiaomi")){
                MiPushClient.setAlias(MyApplication.getInstance(),loginBean.getData().getUserId(),null);
            }else if (deviceBrand.equals("vivo")){
                PushClient.getInstance(MyApplication.getInstance()).
                        bindAlias(loginBean.getData().getUserId(), i -> { });
            }
            EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表
            EventBus.getDefault().post(new HomeRefresh(HomeRefresh.tag));//用来刷新首页的
            if ( newpeople == 1 && loginBean.getData().getIsNewUser().equals("0")){
                startActivity(new Intent(this, NewFreeActivity.class));
                EventBus.getDefault().post(new HomeNewPeopleBus(1));//登陆后刷新首页活动接口
            }else {
                EventBus.getDefault().post(new HomeNewPeopleBus(0));//登陆后刷新首页活动接口
            }
            ToastUtil.show("登录成功");
            setResult(200);
            finish();
        }else {
            ToastUtil.show(loginBean.getMsg());
        }
    }

    @Override
    public void loginWx(LoginBean loginBean, String channel, String openid) {
        if(loginBean.getCode() == 0){
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userAcutalName,loginBean.getData().getRealname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday,loginBean.getData().getBirthday());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender,loginBean.getData().getGender());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade,loginBean.getData().getMemberLevel());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg,loginBean.getData().getAvatar());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName,loginBean.getData().getNickname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone,loginBean.getData().getMobile());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.token,loginBean.getData().getToken());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint,loginBean.getData().getPoint());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindMobile, loginBean.getData().getBindMobile() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeibo, loginBean.getData().getBindWeibo() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin, loginBean.getData().getBindWeixin() + "");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.qrCode, loginBean.getData().getInvitationCode());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userId,loginBean.getData().getUserId());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, loginBean.getData().getRelationId());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.isNewUser, loginBean.getData().getIsNewUser());

            JPushAlias.setAlias(this,loginBean.getData().getUserId());
            String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
            if (deviceBrand.equals("xiaomi")){
                MiPushClient.setAlias(MyApplication.getInstance(),loginBean.getData().getUserId(),null);
            }else if (deviceBrand.equals("vivo")){
                PushClient.getInstance(MyApplication.getInstance()).
                        bindAlias(loginBean.getData().getUserId(), i -> { });
            }
            EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表
            EventBus.getDefault().post(new HomeRefresh(HomeRefresh.tag));//用来刷新首页的
            if (newpeople == 1 && loginBean.getData().getIsNewUser().equals("0")){
                startActivity(new Intent(this, NewFreeActivity.class));
                EventBus.getDefault().post(new HomeNewPeopleBus(1));//登陆后刷新首页活动接口
            }else {
                EventBus.getDefault().post(new HomeNewPeopleBus(0));//登陆后刷新首页活动接口
            }
            ToastUtil.show("登录成功");
            setResult(200);
            finish();
        }else if(loginBean.getCode() == 700){
            if (MyApplication.isJVerify){
                //一键登录页面
                LoginUtil.bindPhone(this, v -> {
                    startActivityForResult(new Intent(InputLoginActivity.this, BindNumberActivity.class)
                                    .putExtra("channel", channel)
                                    .putExtra("openid", openid)
                                    .putExtra("newpeople", newpeople)
                            , 100);
                }, (code, token) -> {
                    //绑定成功
                    mPresenter.jVerifyBind(token,openid,this.bindToLifecycle());
                });
            }else {
                startActivityForResult(new Intent(InputLoginActivity.this, BindNumberActivity.class)
                                .putExtra("channel",channel)
                                .putExtra("openid",openid)
                                .putExtra("newpeople",newpeople)
                        ,100);
            }
        }else {
            ToastUtil.show(loginBean.getMsg());
        }
    }

    @Override
    public void onBind(LoginBean loginBean) {
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userAcutalName,loginBean.getData().getRealname());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday,loginBean.getData().getBirthday());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender,loginBean.getData().getGender());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade,loginBean.getData().getMemberLevel());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg,loginBean.getData().getAvatar());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName,loginBean.getData().getNickname());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone,loginBean.getData().getMobile());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.token,loginBean.getData().getToken());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint,loginBean.getData().getPoint());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindMobile, loginBean.getData().getBindMobile() + "");
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeibo, loginBean.getData().getBindWeibo() + "");
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin, loginBean.getData().getBindWeixin() + "");
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.qrCode, loginBean.getData().getInvitationCode());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userId,loginBean.getData().getUserId());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, loginBean.getData().getRelationId());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.isNewUser, loginBean.getData().getIsNewUser());

        JPushAlias.setAlias(this,loginBean.getData().getUserId());
        String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
        if (deviceBrand.equals("xiaomi")){
            MiPushClient.setAlias(MyApplication.getInstance(),loginBean.getData().getUserId(),null);
        }else if (deviceBrand.equals("vivo")){
            PushClient.getInstance(MyApplication.getInstance()).
                    bindAlias(loginBean.getData().getUserId(), i -> { });
        }
        EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表
        EventBus.getDefault().post(new HomeRefresh(HomeRefresh.tag));//用来刷新首页的
        if (newpeople == 1 && loginBean.getData().getIsNewUser().equals("0")){
            startActivity(new Intent(this, NewFreeActivity.class));
            EventBus.getDefault().post(new HomeNewPeopleBus(1));//登陆后刷新首页活动接口
        }else {
            EventBus.getDefault().post(new HomeNewPeopleBus(0));//登陆后刷新首页活动接口
        }
        ToastUtil.show("登录成功");
        setResult(200);
        LoginUtil.closePage();
        finish();
    }

    public void send(String ticket,String randstr){
        if (!timerStart[0]) {
            if (mTimer != null) {
                mTimer.start();
            }
            timerStart[0] = true;
        }
        mPresenter.pushMessage(mBinding.loginNumber.getText().toString(),ticket,randstr, this.<SuccessBean>bindToLifecycle());
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        if (mTimer != null) {
            mTimer.cancel();
        }
        super.onDestroy();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 222:
                setResult(222);
                finish();
                break;
        }
    }
}
