package com.example.administrator.jipinshop.activity.web.hb;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2020/5/27
 * @Describe
 */
public interface HBWebView2View {

    void onSuccess(ImageBean bean, SHARE_MEDIA share_media);
    void onFile(String error);

    void withdrawInfo(WithdrawInfoBean bean);
}
