package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;

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
        AlibcShowParams alibcShowParams  = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");
        //yhhpass参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcTrade.show(context, new AlibcPage(url), alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
            }
        });
    }

    /**
     * 第三方淘宝登陆
     */
    public static void aliLogin(){
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(new AlibcLoginCallback() {

            @Override
            public void onSuccess(int i) {
                ToastUtil.show("登录成功 ");
                //获取淘宝用户信息
                Log.d("--获取淘宝用户信息", "获取淘宝用户信息: "+AlibcLogin.getInstance().getSession());
                String openid=AlibcLogin.getInstance().getSession().openId;//重复登陆不会变
                String name = AlibcLogin.getInstance().getSession().nick;//重复登陆不会变
                String imgurl = AlibcLogin.getInstance().getSession().avatarUrl;//重复登陆不会变
                String topExpireTime = AlibcLogin.getInstance().getSession().topExpireTime;//重复登陆不会变

                String openSid = AlibcLogin.getInstance().getSession().openSid;//重复登陆不会变，退出后登陆会改变
                String topAccessToken = AlibcLogin.getInstance().getSession().topAccessToken;//重复登陆会变
                String topAuthCode = AlibcLogin.getInstance().getSession().topAuthCode;//重复登陆会变

                Log.d("--淘宝名称图片：",name +imgurl);
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.show("登录失败 ");
            }
        });
    }

    /**
     * 第三方淘宝退出登陆
     */
    public static void aliLogout(){
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(new AlibcLoginCallback() {

            @Override
            public void onSuccess(int i) {
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }
}
