package com.example.administrator.jipinshop.activity.web.invite;

import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2020/10/14
 * @Describe
 */
public interface InviteActionWebView {

    void withdrawInfo(WithdrawInfoBean bean);
    void initShare(SHARE_MEDIA share_media, ShareInfoBean bean);
    void onFlie(String error);
}
