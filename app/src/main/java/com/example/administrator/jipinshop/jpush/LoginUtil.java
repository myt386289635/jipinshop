package com.example.administrator.jipinshop.jpush;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.LoginSettings;

/**
 * @author 莫小婷
 * @create 2020/5/14
 * @Describe 极光一键登录UI
 */
public class LoginUtil {

    //统一自定义一键登录UI
    public static JVerifyUIConfig.Builder initUI(Context context , String title , String buttonText){
        JVerifyUIConfig.Builder builder = new JVerifyUIConfig.Builder()
                .setNavColor(Color.WHITE)
                .setVirtualButtonTransparent(false)
                .setStatusBarDarkMode(true)
                .setStatusBarColorWithNav(true)
                .setNavText(title)
                .setNavTextColor(0xff202020)
                .setNavTextBold(true)
                .setNavTextSize(18)
                .setNavReturnImgPath("nav_back")
                .setNavReturnBtnHeight((int) context.getResources().getDimension(R.dimen.y12))
                .setNavReturnBtnWidth((int) context.getResources().getDimension(R.dimen.x6))
                .setNumberColor(0xff202020)
                .setNumberTextBold(true)
                .setNumberSize(21)
                .setLogoOffsetY((int) context.getResources().getDimension(R.dimen.y40))
                .setNumFieldOffsetY((int) context.getResources().getDimension(R.dimen.y140))
                .setLogBtnTextColor(Color.WHITE)
                .setLogBtnText(buttonText)
                .setLogBtnTextBold(false)
                .setLogBtnTextSize(18)
                .setLogBtnOffsetY((int) context.getResources().getDimension(R.dimen.y175))
                .setSloganHidden(true)
                .setAppPrivacyOne("《极品城用户协议》", RetrofitModule.H5_URL + "agreement.html")
                .setAppPrivacyTwo("《隐私政策》",RetrofitModule.H5_URL + "privacy.html")
                .setAppPrivacyColor(0xffB0B0B0,0xff5194E3)
                .setPrivacyState(true)
                .setPrivacyText("登录代表同意","及","和","")
                .setPrivacyTextSize(12)
                .enableHintToast(true, Toast.makeText(context,"请阅读并勾选协议",Toast.LENGTH_SHORT))
                .setPrivacyCheckboxInCenter(true)
                .setPrivacyWithBookTitleMark(true)
                .setCheckedImgPath("login_agree")
                .setUncheckedImgPath("login_noagree")
                .setPrivacyTextWidth((int) context.getResources().getDimension(R.dimen.x170))
                .setPrivacyCheckboxSize((int) context.getResources().getDimension(R.dimen.x9))
                .setPrivacyNavColor(Color.WHITE)
                .setPrivacyNavTitleTextColor(0xff202020)
                .setPrivacyNavTitleTextSize(18)
                .setAppPrivacyNavTitle1("用户协议")
                .setAppPrivacyNavTitle2("隐私政策")
                .setPrivacyStatusBarColorWithNav(true)
                .setPrivacyStatusBarDarkMode(true)
                .setPrivacyNavTitleTextBold(true)
                .setPrivacyNavReturnBtn(right_black(context))
                .setLoadingView(loading(context),null);
        return builder;
    }

    //手机号登陆工具
    public static void phoneLogin(Context context , View.OnClickListener wx, View.OnClickListener phone, OnGo go){
        JVerifyUIConfig.Builder builder = initUI(context,"手机号登录","一键登录");
        builder
                .addCustomView(wx_login(context), true, (context1, view1) ->{
                    wx.onClick(view1);
                })
                .addCustomView(phone_login(context), true, (context12, view) -> {
                    phone.onClick(view);
                });
        JVerifyUIConfig config = builder.build();
        JVerificationInterface.setCustomUIWithConfig(config);
        jVerify_login(context,go);
    }

    //绑定手机号工具
    public static void bindPhone(Context context, View.OnClickListener listener, OnGo go){
        JVerifyUIConfig.Builder builder = initUI(context,"绑定手机号","一键绑定账号");
        builder.addCustomView(otherPhone_bind(context), true, (context1, view1) ->{
                    listener.onClick(view1);
                });
        JVerifyUIConfig config = builder.build();
        JVerificationInterface.setCustomUIWithConfig(config);
        jVerify_login(context,go);
    }

    //一键登录
    public static void jVerify_login(Context context  , OnGo go ){
        LoginSettings settings = new LoginSettings();
        settings.setAutoFinish(false);//设置登录完成后是否自动关闭授权页
        settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
        settings.setAuthPageEventListener(new AuthPageEventListener() {
            @Override
            public void onEvent(int cmd, String msg) {
            }
        });//授权页开启时与关闭时会调用
        JVerificationInterface.loginAuth(context, settings, (code, content, operator) -> {
            if (code == 6000){
                go.onSuccess(code,content);
            }else{
                if (code != 6002){//用户取消授权返回
                    ToastUtil.show(content);
                }
            }
        });
    }

    //关闭授权页面
    public static void closePage(){
        JVerificationInterface.dismissLoginAuthActivity(true, (code, desc) ->{
            Log.e("moxiaoting", "[dismissLoginAuthActivity] code = " + code + " desc = " + desc);
        });
    }

    //预获取号
    public static void getPhone(Context context){
        JVerificationInterface.clearPreLoginCache();//清除预取号缓存
        JVerificationInterface.preLogin(context, 5000, (code, content) ->{
            Log.e("moxiaoting", "code=" + code + ", message=" + content);
            if (code == 7000){
                //获取成功
                MyApplication.isJVerify = true;
            }else {
                MyApplication.isJVerify = false;
            }
        });
    }

    /***以下是 自定义的view ****/
    private static View wx_login(Context context){
        //自定义的view
        View view = LayoutInflater.from(context).inflate(R.layout.jverify_wx_login,null);
        RelativeLayout item_container = view.findViewById(R.id.item_container);
        ImageView item_image = view.findViewById(R.id.item_image);
        TextView item_name = view.findViewById(R.id.item_name);
        item_image.setImageResource(R.mipmap.wechat);
        item_name.setText("微信登录");
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.setMargins((int) context.getResources().getDimension(R.dimen.x130),
                0,0, (int) context.getResources().getDimension(R.dimen.y180));
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        item_container.setLayoutParams(mLayoutParams1);
        return item_container;
    }

    private static View phone_login(Context context){
        //自定义的view
        View view = LayoutInflater.from(context).inflate(R.layout.jverify_wx_login,null);
        RelativeLayout item_container = view.findViewById(R.id.item_container);
        ImageView item_image = view.findViewById(R.id.item_image);
        TextView item_name = view.findViewById(R.id.item_name);
        item_image.setImageResource(R.mipmap.phone);
        item_name.setText("其他手机号登录");
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.setMargins(0, 0,
                (int) context.getResources().getDimension(R.dimen.x130),
                (int) context.getResources().getDimension(R.dimen.y180));
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        item_container.setLayoutParams(mLayoutParams1);
        return item_container;
    }

    private static View right_black(Context context){
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.nav_back);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.setMargins((int) context.getResources().getDimension(R.dimen.x22), 0,
                0,
                0);
        mLayoutParams1.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setLayoutParams(mLayoutParams1);
        return imageView;
    }

    private static View otherPhone_bind(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.jverify_wx_login,null);
        RelativeLayout item_container = view.findViewById(R.id.item_container);
        ImageView item_image = view.findViewById(R.id.item_image);
        TextView item_name = view.findViewById(R.id.item_name);
        item_image.setImageResource(R.mipmap.phone);
        item_name.setText("绑定其他手机号");
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.setMargins(0, 0, 0,
                (int) context.getResources().getDimension(R.dimen.y180));
        mLayoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        item_container.setLayoutParams(mLayoutParams1);
        return item_container;
    }

    private static View loading(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImgView = (ImageView) v.findViewById(R.id.imgView_loading_dialog_img);
        TextView tipTv = (TextView) v.findViewById(R.id.tv_loading_dialog_tip);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImgView.startAnimation(hyperspaceJumpAnimation);
        tipTv.setVisibility(View.GONE);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
        v.setLayoutParams(mLayoutParams1);
        return v;
    }

    public interface OnGo{
        void onSuccess(int code , String token);
    }
}
