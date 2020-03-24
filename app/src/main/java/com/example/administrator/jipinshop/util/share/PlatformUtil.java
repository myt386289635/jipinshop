package com.example.administrator.jipinshop.util.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.administrator.jipinshop.util.ToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/2/21
 * @Describe 解决QQ无法分享纯文本的问题
 */
public class PlatformUtil {

    // 是否存在QQ客户端
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断手机是否安装微信
     */
    public static boolean isWxAppInstalledAndSupported(Context context) {
        //添加一层用微信api判断，避免部分手机判断失效问题
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, "wxfd2e92db2568030a");
        boolean bIsWXAppInstalledAndSupported = wxApi.isWXAppInstalled();
        if (bIsWXAppInstalledAndSupported) {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equals("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static void shareQQ(Context mContext, String content) {
        if (PlatformUtil.isQQClientAvailable(mContext)) {
            Intent sendIntent =new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, content);
            sendIntent.setType("text/plain");
            try {
                sendIntent.setClassName("com.tencent.mobileqq",
                        "com.tencent.mobileqq.activity.JumpActivity");
                Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
                if (chooserIntent ==null) {
                    return;
                }
                mContext.startActivity(chooserIntent);
            }catch (Exception e) {
                mContext.startActivity(sendIntent);
            }
        }else {
            ToastUtil.show("您需要安装QQ客户端");
        }
    }

    //分享多图到微信
    public static Intent shareWX_images(Context mContext, ArrayList<Uri> list) {
        if (PlatformUtil.isWxAppInstalledAndSupported(mContext)) {
            Intent sendIntent =new Intent();
            sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            sendIntent.setType("image/*");
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,list);
                sendIntent.setClassName("com.tencent.mm",
                            "com.tencent.mm.ui.tools.ShareImgUI");
                Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
                if (chooserIntent ==null) {
                    return null;
                }
                return chooserIntent;
            }catch (Exception e) {
                return sendIntent;
            }
        }else {
            ToastUtil.show("您需要安装微信客户端");
        }
        return null;
    }

    //分享多图到QQ
    public static Intent shareQQ_images(Context mContext, ArrayList<Uri> list) {
        if (PlatformUtil.isQQClientAvailable(mContext)) {
            Intent sendIntent =new Intent();
            sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            sendIntent.setType("image/*");
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,list);
                sendIntent.setClassName("com.tencent.mobileqq",
                        "com.tencent.mobileqq.activity.JumpActivity");
                Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
                if (chooserIntent ==null) {
                    return null;
                }
                return chooserIntent;
            }catch (Exception e) {
               return sendIntent;
            }
        }else {
            ToastUtil.show("您需要安装QQ客户端");
        }
        return null;
    }

    //分享多图到朋友圈 微信7.0以后不支持分享多图到朋友圈，所以仅打开微信即可
    public static Intent sharePYQ_images(Context mContext) {
        if (PlatformUtil.isWxAppInstalledAndSupported(mContext)) {
            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            return intent;
        }else {
            ToastUtil.show("您需要安装微信客户端");
        }
        return null;
    }
}
