package com.example.administrator.jipinshop.activity.sign.market;

import android.app.Dialog;

/**
 * @author 莫小婷
 * @create 2020/7/13
 * @Describe
 */
public interface MarketView {

    /***上传图片**/
    void uploadPicSuccess(Dialog mDialog, String picPath);
    void uploadPicFailed(String error);

    void OpinionSuccess(String content);
}
