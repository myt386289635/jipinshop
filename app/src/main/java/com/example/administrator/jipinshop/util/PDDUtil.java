package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * @author 莫小婷
 * @create 2020/4/24
 * @Describe 拼多多跳转
 */
public class PDDUtil {


    /**
     * 判断是否安装拼多多
     */
    public static boolean checkHasInstalledApp(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        String pkgName = "com.xunmeng.pinduoduo";
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
     * 跳转到拼多多
     */
    public static void jumpPDD(Context context,String url,String errorUrl){
        if (checkHasInstalledApp(context)){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }else {
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(errorUrl));
            context.startActivity(intent);
        }
    }

}
