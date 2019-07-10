package com.example.administrator.jipinshop.activity.home.classification.encyclopedias;

import com.example.administrator.jipinshop.bean.FindDetailBean;

/**
 * @author 莫小婷
 * @create 2019/7/10
 * @Describe
 */
public interface EncyclopediasView {

    void onSuccess(FindDetailBean bean);
    void onFile(String error);
}
