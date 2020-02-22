package com.example.administrator.jipinshop.util.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.example.administrator.jipinshop.util.ToastUtil;

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

}
