package com.example.administrator.jipinshop;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.util.DebugHelper;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 莫小婷
 * @create 2018/7/30
 * @Describe
 */
public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;
    private static MyApplication instance;
    public static final String imag = "http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/03/14/c0391a6c1efab3fe00911b04e8cedca4.jpg";
    private  int mNotificationNum = 0;

    public int getNotificationNum() {
        return mNotificationNum;
    }

    public void setNotificationNum(int notificationNum) {
        mNotificationNum = notificationNum;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //内存低的时候清除Glide缓存
        Glide.get(this).clearMemory();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mApplicationComponent =
                DaggerApplicationComponent
                        .create();
        mApplicationComponent.inject(this);
        Utils.init(instance);
        DebugHelper.syncIsDebug(this);
        //初始化阿里百川
        initAlibcTradeSDK();

        //初始化友盟
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5b716da48f4a9d4859000095","umeng",UMConfigure.DEVICE_TYPE_PHONE, "");
        //微信(已修改)
        PlatformConfig.setWeixin("wx5e541cafac68980e", "b24ea3348fcda68d5ae9bdfabbeb867d");
        //新浪微博(已修改)
        PlatformConfig.setSinaWeibo("1438884882", "2a0c703950acf79e36f14b222ac54e00","http://sns.whalecloud.com/sina2/callback");
        //腾讯（已修改）
        PlatformConfig.setQQZone("1107605787", "0wJh63XVNMCo307r");

        //极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
            ///没有登陆
            JPushInterface.stopPush(this);//停止推送
        }

        if(DebugHelper.isDebug()){
            //初始化LeakCanary
            mRefWatcher = LeakCanary.install(this);
        }

        initBugly();
    }
    private RefWatcher mRefWatcher;
    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    /***
     * 初始化阿里百川sdk
     */
    private void initAlibcTradeSDK() {

        //电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Log.e("AlibcTradeSDK", "初始化阿里百e川成功");
//                Toast.makeText(MyApplication.this, "初始化阿里百e川成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.e("AlibcTradeSDK", "初始化阿里百川失败,错误码=" + code + " / 错误消息=" + msg);
//                Toast.makeText(MyApplication.this, "初始化阿里百川失败,错误码=" + code + " / 错误消息=" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化bugly
     */
    private void initBugly(){
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setBuglyLogUpload(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "5d46063289", false,strategy);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
