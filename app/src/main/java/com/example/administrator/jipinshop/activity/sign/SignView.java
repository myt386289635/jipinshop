package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.view.inputmethod.InputMethodManager;

import com.example.administrator.jipinshop.bean.ActionHBBean;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public interface SignView {

    /**签到成功*/
    void signSuc(SignInsertBean signInsertBean);
    void signFile(int code , String error);

    /*获取每日任务列表**/
    void getDayList(DailyTaskBean bean);

    void onCodeSuc(Dialog dialog, InputMethodManager inputManager, SuccessBean bean);
    void onFile(String error);

    void onSuccess(MallBean bean);

    void onHBID(ActionHBBean bean);
    void onHBFlie();
}
