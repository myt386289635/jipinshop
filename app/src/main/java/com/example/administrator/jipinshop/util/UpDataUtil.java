package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

/**
 * @author 莫小婷
 * @create 2018/10/8
 * @Describe 版本更新
 */
public class UpDataUtil {
    private static UpDataUtil upDataUtil;

    public static UpDataUtil newInstance() {
        if (upDataUtil == null) {
            upDataUtil = new UpDataUtil();
        }
        return upDataUtil;
    }

    /**
     * 蒲公英的下载方法 功能是实现的 （ps:但是产品说不要，因为花钱）
     * @param activity
     */
    public void download(Activity activity) {
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)          //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)      // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is no new version");
                    }
                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        //有更新回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());
                        if(Integer.parseInt(appBean.getVersionCode()) > getPackageVersionCode()){
                            new AlertDialog.Builder(activity)
                                    .setTitle("版本更新")
                                    .setMessage(appBean.getReleaseNote())
                                    .setNegativeButton(
                                            "下次再说",
                                            (dialog, which) -> dialog.dismiss())
                                    .setPositiveButton("下载",
                                            (dialog, which) -> {
                                        //调用以下方法，DownloadFileListener 才有效；
                                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                                        PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                                    }).show();

                        }

                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                        Log.e("pgyer", "check update failed ", e);
                    }
                })
                //注意 ：
                //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
                //想要使用蒲公英的默认下载进度的UI则不设置此方法
//                .setDownloadFileListener(new DownloadFileListener() {
//                    @Override
//                    public void downloadFailed() {
//                        //下载失败
//                        Log.e("pgyer", "download apk failed");
//                    }
//
//                    @Override
//                    public void downloadSuccessful(Uri uri) {
//                        Log.e("pgyer", "download apk failed");
//                        // 使用蒲公英提供的安装方法提示用户 安装apk
//                        PgyUpdateManager.installApk(uri);
//                    }
//
//                    @Override
//                    public void onProgressUpdate(Integer... integers) {
//                        Log.e("pgyer", "update download apk progress" + integers);
//                    }})
                .register();
    }

    /**
     * 跳转浏览器下载apk
     */
    public void downloadApk(Context context,String varsonNum,Boolean tag,String content, String url){
        if(tag){
            //必须强制更新
            DialogUtil.UpDateDialog(context, varsonNum , content, v -> {
                try{
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
                    context.startActivity(intent);
                }catch (Exception e){
                    Intent ExeIntent = new Intent();
                    ExeIntent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    ExeIntent.setData(content_url);
                    context.startActivity(ExeIntent);
                }
            });
        }else {
            //可以取消
            DialogUtil.UpDateDialog(context, varsonNum , content, v -> {
                try{
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
                    context.startActivity(intent);
                }catch (Exception e){
                    Intent ExeIntent = new Intent();
                    ExeIntent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    ExeIntent.setData(content_url);
                    context.startActivity(ExeIntent);
                }
            });
        }

    }

    public static int getPackageVersionCode() {
        try {
            Context ctx = MyApplication.getInstance();
            return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
