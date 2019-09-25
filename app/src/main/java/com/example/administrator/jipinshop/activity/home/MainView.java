package com.example.administrator.jipinshop.activity.home;

import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;

/**
 * @author 莫小婷
 * @create 2018/11/22
 * @Describe
 */
public interface MainView {

    void onSuccess(AppVersionbean versionbean);

    void onDialogSuc(PopInfoBean bean);
}
