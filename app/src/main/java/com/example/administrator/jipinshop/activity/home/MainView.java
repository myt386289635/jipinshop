package com.example.administrator.jipinshop.activity.home;

import android.app.Dialog;

import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TklBean;

/**
 * @author 莫小婷
 * @create 2018/11/22
 * @Describe
 */
public interface MainView {

    void onSuccess(AppVersionbean versionbean);
    void onFile();

    void onDialogSuc(PopInfoBean bean);
    void onDialogFile();

    void onTklDialog(TklBean bean,String tkl);

    void onInvitationSuc( Dialog dialog);
    void onInvitationFile(String error);

    void onAdList(SucBean<EvaluationTabBean.DataBean.AdListBean> adListBeanSucBean);
}
