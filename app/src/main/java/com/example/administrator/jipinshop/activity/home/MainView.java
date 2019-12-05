package com.example.administrator.jipinshop.activity.home;

import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
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
}
