package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.view.inputmethod.InputMethodManager;

import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public interface SignView {

    /*查询签到信息*/
    void getInfoSuc(SignBean signBean);
    void getInfoFaile(String error);

    /**签到成功*/
    void signSuc(SignInsertBean signInsertBean);
    void signFaile(String error);

    /*获取每日任务列表**/
    void getDayList(DailyTaskBean bean);

    void onCodeSuc(Dialog dialog, InputMethodManager inputManager, SuccessBean bean);
    void onFile(String error);
}
