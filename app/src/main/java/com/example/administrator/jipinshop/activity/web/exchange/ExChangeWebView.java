package com.example.administrator.jipinshop.activity.web.exchange;

import com.example.administrator.jipinshop.bean.ShareInfoBean;

/**
 * @author 莫小婷
 * @create 2021/5/26
 * @Describe
 */
public interface ExChangeWebView {

    void initShare(ShareInfoBean bean);
    void onFile(String error);

}
