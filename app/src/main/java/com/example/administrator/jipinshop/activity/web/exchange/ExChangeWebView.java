package com.example.administrator.jipinshop.activity.web.exchange;

import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2021/5/26
 * @Describe
 */
public interface ExChangeWebView {

    void initShare(ShareInfoBean bean);
    void onFile(String error);

    void onShare(ShareInfoBean bean , SHARE_MEDIA share_media);
}
