package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe
 */
public class TaoBaoUtil {

    /****
     * 跳转淘宝首页
     */
    public static void openAliHomeWeb(Activity context, String url) {
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Native);
        showParams.setClientType("taobao");
        showParams.setBackUrl("alisdk://");
        showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpH5);

        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");

        Map<String, String> trackParams = new HashMap<>();
        AlibcTrade.openByUrl(context, "", url, null,
                new WebViewClient(), new WebChromeClient(),
                showParams, taokeParams, trackParams, new AlibcTradeCallback() {
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
            public void onFailure(int code, String msg) {
                ToastUtil.show("登录失败 ");
            }
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
}
