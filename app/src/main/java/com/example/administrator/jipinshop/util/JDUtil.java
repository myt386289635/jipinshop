package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;

/**
 * @author 莫小婷
 * @create 2020/4/28
 * @Describe 京东工具
 */
public class JDUtil {

    public static void init(){
        String appKey = "dc7460a32867d066f8f2770d23e13760";
        String keySecret = "fdce678f736647138bf220fa24363426";
        KeplerApiManager.asyncInitSdk(MyApplication.getInstance(), appKey, keySecret,
                new AsyncInitListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("Kepler", "Kepler asyncInitSdk onSuccess ");
                    }

                    @Override
                    public void onFailure() {
                        Log.e("Kepler", "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
                    }
                });
    }

    public static void openJD(Context context, String webUrl){
        //这个是即时性参数  可以设置
        if (TextUtils.isEmpty(webUrl)){
            ToastUtil.show("无跳转链接，跳转失败");
            return;
        }
        Handler handler = new Handler();
        KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();
        OpenAppAction mOpenAppAction = (status, url) -> {
            //没有在UI线程里工作
            handler.post(() -> {
                switch (status){
                    case OpenAppAction.OpenAppAction_result_NoJDAPP://未安装京东app
                        ToastUtil.show("您未安装京东app，正在为您打开浏览器");
                    case OpenAppAction.OpenAppAction_result_ErrorScheme://呼起协议异常
                    case OpenAppAction.OpenAppAction_result_BlackUrl://url不在白名单
                    case OpenAppAction.OpenAppAction_result_NetError://网络错误
                        //异常处理 通过浏览器打开链接
                        context.startActivity(new Intent(context, WebActivity.class)
                                .putExtra(WebActivity.url, webUrl)
                                .putExtra(WebActivity.title,"京东")
                        );
                        break;
                }
            });
        };
        KeplerApiManager.getWebViewService().openAppWebViewPage(context,
                webUrl,
                mKeplerAttachParameter,
                mOpenAppAction);
    }

}
