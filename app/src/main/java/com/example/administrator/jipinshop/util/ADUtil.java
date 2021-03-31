package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.view.ViewGroup;

import com.qubian.mob.AdManager;

/**
 * @author 莫小婷
 * @create 2021/3/31
 * @Describe 第三方广告工具类
 */
public class ADUtil {

    //开屏广告
    public static void playAD(Activity activity, ViewGroup viewGroup , Jump jump){
        int error = 0;
        onAd(error,activity,viewGroup,jump);
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

    public interface Jump{
        void  onJump();
    }
}
