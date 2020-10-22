package com.example.administrator.jipinshop.activity;

import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.ImageBean;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public interface WebVieww {

    void onFile(String error);

    void onBuyLinkSuccess(ClickUrlBean bean);

    void onAction(ImageBean bean);
    void onActionFile();
}
