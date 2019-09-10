package com.example.administrator.jipinshop.activity.info;

import android.app.Dialog;

import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/9/29
 * @Describe
 */
public interface MyInfoView {

    void EditBirthDaySuccess(SuccessBean successBean,String date);

    void EditGenderSuccess(SuccessBean successBean,String date);

    void EditUserNickImgSuc(SuccessBean successBean,String date);

    void EditUserBg(SuccessBean successBean,String date);

    /***上传图片**/
    void uploadPicSuccess(Dialog mDialog,String picPath);
    void uploadPicFailed(String error);
}
