package com.example.administrator.jipinshop.activity.web.dzp;

import com.example.administrator.jipinshop.bean.PrizeLogBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2020/7/1
 * @Describe
 */
public interface BigWheelWebView {

    void onSuccess(PrizeLogBean bean);
    void onFlie(String error);

    void initShare(SHARE_MEDIA share_media, ShareInfoBean bean);
}
