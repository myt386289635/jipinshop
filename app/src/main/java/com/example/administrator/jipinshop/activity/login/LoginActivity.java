package com.example.administrator.jipinshop.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.login.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.login.input.InputLoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.databinding.LoginBinding;
import com.example.administrator.jipinshop.jpush.JPushAlias;
import com.example.administrator.jipinshop.jpush.LoginUtil;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe 登陆页面
 */
public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    public static final String refresh = "login2AllRefresh";

    @Inject
    LoginPresenter mPresenter;
    private LoginBinding mBinding;

    private int newpeople = 0;//判断是否是从弹框点击来的  0 不是从弹框点击来的  1 是从新人弹框点击来的

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.login);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        newpeople = getIntent().getIntExtra("newpeople",0);
        mPresenter.setView(this);
        String content= "登录即代表同意《极品城用户协议》及《隐私政策》";
        SpannableString string = new SpannableString(content);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(LoginActivity.this, WebActivity.class)
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
                startActivity(new Intent(LoginActivity.this, WebActivity.class)
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
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    public void loginWx(LoginBean loginBean,String channel,String openid) {
        if(loginBean.getCode() == 0){
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userAcutalName,loginBean.getData().getRealname());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday,loginBean.getData().getBirthday());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender,loginBean.getData().getGender());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade,loginBean.getData().getRole() +"");
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

            JPushAlias.setAlias(this,loginBean.getData().getUserId());
            EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表

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
                    startActivityForResult(new Intent(LoginActivity.this, BindNumberActivity.class)
                                    .putExtra("channel", channel)
                                    .putExtra("openid", openid)
                                    .putExtra("newpeople", newpeople)
                            , 100);
                }, (code, token) -> {
                    //绑定成功
                    mPresenter.jVerifyBind(token,openid,this.bindToLifecycle());
                });
            }else {
                startActivityForResult(new Intent(LoginActivity.this, BindNumberActivity.class)
                                .putExtra("channel", channel)
                                .putExtra("openid", openid)
                                .putExtra("newpeople", newpeople)
                        , 100);
            }
        }else {
            ToastUtil.show(loginBean.getMsg());
        }
    }

    //一键登录和一键绑定成功
    @Override
    public void onSuccess(LoginBean loginBean) {
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userAcutalName,loginBean.getData().getRealname());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday,loginBean.getData().getBirthday());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender,loginBean.getData().getGender());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade,loginBean.getData().getRole() +"");
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

        JPushAlias.setAlias(this,loginBean.getData().getUserId());
        EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表

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

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        final Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(LoginActivity.this, "");
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
                    mPresenter.thirdLogin(access_token,openid,"1",LoginActivity.this.bindToLifecycle());
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
                UMShareAPI.get(LoginActivity.this).deleteOauth(LoginActivity.this, share_media, new UMAuthListener() {
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
        if (requestCode == 100 && resultCode == 222){
            //从绑定手机页面回来本页
            setResult(200);
            finish();
        }else if (requestCode == 300 && resultCode == 222){
            //从手机登录页面跳转绑定手机成功回来本页
            setResult(200);
            finish();
        }else if (requestCode == 300 && resultCode == 200){
            //从手机登录页面成功回来本页
            setResult(200);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_wx:
                //点击微信登陆
                authorization(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.title_back:
                setResult(400);
                finish();
                break;
            case R.id.login_input:
                if (MyApplication.isJVerify){
                    //一键登录页面
                    LoginUtil.phoneLogin(this, v -> {
                        //微信登陆
                        authorization(SHARE_MEDIA.WEIXIN);
                    }, v -> {
                        //手机号登陆
                        startActivityForResult(new Intent(LoginActivity.this, InputLoginActivity.class)
                                        .putExtra("newpeople", newpeople)
                                , 300);
                    }, (code, token) -> {
                        //授权登陆成功
                        mPresenter.jVerifyLogin(token,this.bindToLifecycle());
                    });
                }else {
                    startActivityForResult(new Intent(LoginActivity.this, InputLoginActivity.class)
                                    .putExtra("newpeople", newpeople)
                            , 300);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(400);
        super.onBackPressed();
    }
}
