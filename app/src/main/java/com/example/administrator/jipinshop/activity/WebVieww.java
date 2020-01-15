package com.example.administrator.jipinshop.activity;

import com.example.administrator.jipinshop.bean.ImageBean;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public interface WebVieww {

    void onSuccess();

    void onFile(String error);

    void onBuyLinkSuccess(ImageBean bean);
}
