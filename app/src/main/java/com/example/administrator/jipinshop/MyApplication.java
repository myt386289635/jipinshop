package com.example.administrator.jipinshop;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.util.DebugHelper;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author 莫小婷
 * @create 2018/7/30
 * @Describe
 */
public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;
    private static MyApplication instance;
    private  int mNotificationNum = 0;
    public static boolean isJVerify = false;//是否可以一键登录  默认否
    private RefWatcher mRefWatcher;

    public int getNotificationNum() {
        return mNotificationNum;
    }

    public void setNotificationNum(int notificationNum) {
        mNotificationNum = notificationNum;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        String curProcessName = getProcessName(android.os.Process.myPid());
        if (curProcessName!= null && !curProcessName.equals(getPackageName())) {
            return;
        }
        if(level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        String curProcessName = getProcessName(android.os.Process.myPid());
        if (curProcessName!= null && !curProcessName.equals(getPackageName())) {
            return;
        }
        //内存低的时候清除Glide缓存
        Glide.get(this).clearMemory();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        String curProcessName = getProcessName(android.os.Process.myPid());
        if (curProcessName!= null && !curProcessName.equals(getPackageName())) {
            return;
        }
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DebugHelper.syncIsDebug(this);
        String curProcessName = getProcessName(android.os.Process.myPid());
        if (curProcessName!= null && !curProcessName.equals(getPackageName())) {
            return;
        }
        instance = this;
        mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                        .build();
        mApplicationComponent.inject(this);
        Utils.init(instance);

        if(DebugHelper.isDebug()){
            //初始化LeakCanary
            mRefWatcher = LeakCanary.install(this);
        }

    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
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
