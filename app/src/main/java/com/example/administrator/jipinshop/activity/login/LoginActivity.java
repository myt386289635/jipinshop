package com.example.administrator.jipinshop.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.info.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.databinding.LoginBinding;
import com.example.administrator.jipinshop.jpush.JPushAlias;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.tencent.captchasdk.TCaptchaDialog;
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

    public static final String tag = "userMoney";
    public static final String refresh = "login2AllRefresh";

    @Inject
    LoginPresenter mPresenter;

    private CountDownTimer mTimer;
    private Boolean[] timerEnd = {false};
    private Dialog mDialog;
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
        mBinding.loginNumber.requestFocus();
        mBinding.loginButton.setEnabled(false);
        mBinding.loginGetCode.setEnabled(false);
        mPresenter.setView(this);
        mTimer = mPresenter.initTimer(this, mBinding.loginGetCode);
        mPresenter.initLoginButton(mBinding.loginNumber, mBinding.loginCode, mBinding.loginButton, mBinding.loginGetCode,timerEnd);

        String str = "登录即视为同意<font color='#4A90E2'>《极品城用户协议》</font>";
        mBinding.loginProtocol.setText(Html.fromHtml(str));

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在登录...");
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

    /**
     * 倒计时结束
     */
    @Override
    public void timerEnd() {
        timerEnd[0] = false;
        if(mBinding.loginNumber.getText().toString().length() == 11){
            mBinding.loginGetCode.setEnabled(true);
            mBinding.loginGetCode.setBackgroundResource(R.drawable.bg_login2);
        }else {
            mBinding.loginGetCode.setEnabled(false);
            mBinding.loginGetCode.setBackgroundResource(R.drawable.bg_login);
        }
    }

    /**
     * 登陆处理
     * @param loginBean
     */
    @Override
    public void loginSuccess(LoginBean loginBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (loginBean.getCode() == 0){
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
            EventBus.getDefault().post(new EditNameBus(LoginActivity.tag,loginBean.getData().getFansCount()+""
                    ,loginBean.getData().getVoteCount()+"",loginBean.getData().getFollowCount() + ""));//刷新登陆后我的页面
            EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表

            if ( newpeople == 1){
                startActivity(new Intent(this, NewPeopleActivity.class));
            }else {
                EventBus.getDefault().post(new HomeNewPeopleBus(loginBean.getData().getAddPoint()));//登陆后刷新首页活动接口
            }
            ToastUtil.show("登录成功");
            setResult(200);
            finish();
        }else {
            ToastUtil.show(loginBean.getMsg());
        }
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
            EventBus.getDefault().post(new EditNameBus(LoginActivity.tag,loginBean.getData().getFansCount()+""
                    ,loginBean.getData().getVoteCount()+"",loginBean.getData().getFollowCount() + ""));//刷新登陆后我的页面
            EventBus.getDefault().post(new CommonEvaluationBus(LoginActivity.refresh));//用来刷新商品、评测、发现详情以及评论列表

            if (newpeople == 1){
                startActivity(new Intent(this, NewPeopleActivity.class));
            }else {
                EventBus.getDefault().post(new HomeNewPeopleBus(loginBean.getData().getAddPoint()));//登陆后刷新首页活动接口
            }
            ToastUtil.show("登录成功");
            setResult(200);
            finish();
        }else if(loginBean.getCode() == 700){
            startActivityForResult(new Intent(LoginActivity.this,BindNumberActivity.class)
                    .putExtra("channel",channel)
                    .putExtra("openid",openid)
                    ,100);
        }
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
        switch (resultCode){
            case 222:
                if ( newpeople == 1){
                    startActivity(new Intent(this, NewPeopleActivity.class));
                }else {
                    EventBus.getDefault().post(new HomeNewPeopleBus(0));//登陆后刷新首页活动接口
                }
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                //点击登陆按钮
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                String invitationCode = "";
                if (!TextUtils.isEmpty(mBinding.loginQrcode.getText().toString())){
                    invitationCode = mBinding.loginQrcode.getText().toString();
                }
                mPresenter.login(mBinding.loginNumber.getText().toString(),mBinding.loginCode.getText().toString(),
                        invitationCode ,this.<LoginBean>bindToLifecycle());
                break;
            case R.id.login_getCode:
                //点击获取验证码
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
            case R.id.login_wx:
                //点击微信登陆
                DialogUtil.LoginDialog(this, "“极品城”想要打开“微信”", view1 -> authorization(SHARE_MEDIA.WEIXIN));
                break;
            case R.id.login_wb:
                //点击微博登陆
                DialogUtil.LoginDialog(this, "“极品城”想要打开“微博”", view12 -> authorization(SHARE_MEDIA.SINA));
                break;
            case R.id.login_qq:
                //点击QQ登陆
                DialogUtil.LoginDialog(this, "“极品城”想要打开“QQ”", view13 -> authorization(SHARE_MEDIA.QQ));
                break;
            case R.id.login_protocol:
                //点击用户协议
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"agreement.html")
                        .putExtra(WebActivity.title,"用户协议")
                );
                break;
            case R.id.title_back:
                setResult(400);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(400);
        super.onBackPressed();
    }

    public void send(String ticket,String randstr){
        if (!timerEnd[0]) {
            if (mTimer != null) {
                mTimer.start();
            }
            mBinding.loginGetCode.setEnabled(false);
            timerEnd[0] = true;
        }
        mPresenter.pushMessage(this,mBinding.loginNumber.getText().toString(),ticket,randstr,this.<SuccessBean>bindToLifecycle());
    }
}
