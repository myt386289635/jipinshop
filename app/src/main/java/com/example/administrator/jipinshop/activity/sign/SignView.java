package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.view.inputmethod.InputMethodManager;

import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public interface SignView {

//    /**签到成功*/
    void signSuc(SignInsertBean signInsertBean);
    void noSignSuc(SignInsertBean bean);//补签成功

    /*赚钱页面**/
    void onIndex(DailyTaskBean bean);
    void onCodeSuc(Dialog dialog, InputMethodManager inputManager, SuccessBean bean);
    void onFile(String error);
    void onSuccess(MallBean bean);
    void onTeacher(TeacherBean bean);
    void initShare(SHARE_MEDIA share_media, ShareInfoBean bean);
}
