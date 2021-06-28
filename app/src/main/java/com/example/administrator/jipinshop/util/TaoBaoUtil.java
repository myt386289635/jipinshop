package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe
 */
public class TaoBaoUtil {

    @Inject
    AppStatisticalUtil mStatisticalUtil;

    public TaoBaoUtil(Context context) {
        ApplicationComponent mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(context))
                        .build();
        mApplicationComponent.inject(this);
    }

    /****
     * 跳转淘宝首页
     */
    public static void openAliHomeWeb(Activity context, String url,String id) {
        if (TextUtils.isEmpty(id)){
            if (TextUtils.isEmpty(url)){
                ToastUtil.show("跳转链接为空");
                return;
            }
            AlibcShowParams showParams = new AlibcShowParams();
            showParams.setOpenType(OpenType.Native);
            showParams.setClientType("taobao");
            showParams.setBackUrl("alisdk://");
            showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpH5);

            Map<String, String> trackParams = new HashMap<>();
            AlibcTrade.openByUrl(context, "", url, null,
                    new WebViewClient(), new WebChromeClient(),
                    showParams, null, trackParams, new AlibcTradeCallback() {
                        @Override
                        public void onTradeSuccess(AlibcTradeResult tradeResult) {
                            AlibcLogger.i("AlibcTradeSDK", "request success");
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            AlibcLogger.e("AlibcTradeSDK", "code=" + code + ", msg=" + msg);
                            if (code == -1) {
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            AlibcBasePage page = new AlibcDetailPage(id);
            AlibcShowParams showParams = new AlibcShowParams();
            showParams.setOpenType(OpenType.Native);
            showParams.setClientType("taobao");
            showParams.setBackUrl("alisdk://");
            showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpH5);

            Map<String, String> trackParams = new HashMap<>();

            AlibcTrade.openByBizCode(context, page, null, new WebViewClient(),
                    new WebChromeClient(), "detail", showParams, null,
                    trackParams, new AlibcTradeCallback() {
                        @Override
                        public void onTradeSuccess(AlibcTradeResult tradeResult) {
                            // 交易成功回调（其他情形不回调）
                            AlibcLogger.i("AlibcTradeSDK", "open detail page success");
                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            // 失败回调信息
                            AlibcLogger.e("AlibcTradeSDK", "code=" + code + ", msg=" + msg);
                        }
                    });
        }
    }

    /**
     * 跳转到淘宝
     */
    public static void jumpTB(Context context,String url,String errorUrl){
        if (checkHasInstalledApp(context)){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }else {
            ToastUtil.show("您未安装淘宝app，正在为您打开浏览器");
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(errorUrl));
            context.startActivity(intent);
        }
    }

    /**
     * 判断是否安装淘宝
     */
    public static boolean checkHasInstalledApp(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        String pkgName = "com.taobao.taobao";
        boolean app_installed;
        try {
            pm.getPackageInfo(pkgName, PackageManager.GET_GIDS);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        } catch (RuntimeException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /**
     * 第三方淘宝登陆
     */
    public static void aliLogin(LoginResLisenter loginResLisenter){
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                //获取淘宝用户信息
//                Log.d("--获取淘宝用户信息", "获取淘宝用户信息: "+AlibcLogin.getInstance().getSession());
//                String openid=AlibcLogin.getInstance().getSession().openId;//重复登陆不会变
//                String name = AlibcLogin.getInstance().getSession().nick;//重复登陆不会变
//                String imgurl = AlibcLogin.getInstance().getSession().avatarUrl;//重复登陆不会变
//                String topExpireTime = AlibcLogin.getInstance().getSession().topExpireTime;//重复登陆不会变
//
//                String openSid = AlibcLogin.getInstance().getSession().openSid;//重复登陆不会变，退出后登陆会改变
                String topAccessToken = AlibcLogin.getInstance().getSession().topAccessToken;//重复登陆会变
//                String topAuthCode = AlibcLogin.getInstance().getSession().topAuthCode;//重复登陆会变
                loginResLisenter.onLoginResult(topAccessToken);
            }

            @Override
            public void onFailure(int code, String msg) { }
        });
    }

    public interface LoginResLisenter{
        void onLoginResult(String topAuthCode);
    }

    /**
     * 第三方淘宝退出登陆
     */
    public static void aliLogout(){
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(new AlibcLoginCallback() {

            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }

    //统一授权管理
    public static void openTB(Context context , OnItem listener){
        TaoBaoUtil taoBaoUtil = new TaoBaoUtil(context);//为了统计
        String specialId2 = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "");
        if (TextUtils.isEmpty(specialId2) || specialId2.equals("null")) {
            DialogUtil.TBLoginDialog(context, v -> {
                taoBaoUtil.addEvent("auth_tb_click");
                TaoBaoUtil.aliLogin(topAuthCode -> {
                    context.startActivity(new Intent(context, TaoBaoWebActivity.class)
                            .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                            .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                    );
                });
            }, v -> {
                taoBaoUtil.addEvent("auth_tb_close");
            });
        } else {
            if (listener != null) listener.go();
        }
    }

    //商品详情第一次进入时，需要淘宝授权弹框
    public static void openStartTB(Context context , OnItem listener){
        TaoBaoUtil taoBaoUtil = new TaoBaoUtil(context);//为了统计
        String specialId2 = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "");
        if (TextUtils.isEmpty(specialId2) || specialId2.equals("null")) {
            DialogUtil.TBLoginDialog(context, v -> {
                if (listener != null) listener.go();
                taoBaoUtil.addEvent("auth_tb_click");
                TaoBaoUtil.aliLogin(topAuthCode -> {
                    context.startActivity(new Intent(context, TaoBaoWebActivity.class)
                            .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                            .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                    );
                });
            }, v -> {
                taoBaoUtil.addEvent("auth_tb_close");
                if (listener != null) listener.go();
            });
        } else {
            if (listener != null) listener.go();
        }
    }

    public void addEvent(String eventId ){
        mStatisticalUtil.addEvent(eventId);
    }

    public interface OnItem{
        void go();
    }
}
