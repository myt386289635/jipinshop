package com.example.administrator.jipinshop.activity.setting.about;

import com.example.administrator.jipinshop.bean.AppVersionbean;

/**
 * @author 莫小婷
 * @create 2020/5/15
 * @Describe
 */
public interface AboutView {

    void onSuccess(AppVersionbean versionbean);
    void onFile(String error);
}
