package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.util.update.UpDataUtil;
import com.qubian.mob.AdManager;

/**
 * @author 莫小婷
 * @create 2021/3/31
 * @Describe 第三方广告工具类
 */
public class ADUtil {

    //开屏广告
    public static void playAD(Activity activity, ViewGroup viewGroup , Jump jump){
        if (ifHW()){
            //华为手机进入
            if (UpDataUtil.getPackageVersionCode() >= SPUtils.getInstance().getInt(CommonDate.VersionCode, 76)){
                //当前版本大于或等于服务器版本 （当前是最新版）
                if (SPUtils.getInstance().getInt(CommonDate.OPEN, 0) == 1){
                    //通过
                    int error = 0;
                    onAd(error,activity,viewGroup,jump);
                }else {
                    //版本还在审核中
                    jump.onJump();
                }
            }else {
                //目前是老版本
                int error = 0;
                onAd(error,activity,viewGroup,jump);
            }
        }else {
            //非华为手机进入
            int error = 0;
            onAd(error,activity,viewGroup,jump);
        }
    }

    //开屏广告
    private static void onAd(int i , Activity activity , ViewGroup viewGroup , Jump jump){
        int[] error = {i};
        AdManager.loadSplashAd("1371288177377034333", "", "", activity, viewGroup, new AdManager.SplashAdLoadListener() {
            @Override
            public void onAdFail(String s) {
                error[0]++;
                if (error[0] >= 2){
                    jump.onJump();
                }else {
                    onAd(error[0],activity,viewGroup,jump);
                }
            }

            @Override
            public void onAdDismiss() {
                jump.onJump();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                jump.onJump();
            }
        });
    }

    public static boolean ifHW(){
        String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
        boolean isHW;
        if (deviceBrand.equals("huawei")){
            isHW = true;
        }else {
            isHW = false;
        }
        return isHW;
    }

    public interface Jump{
        void  onJump();
    }
}
