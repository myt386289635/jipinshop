package com.example.administrator.jipinshop.activity.member.zero;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public interface ZeroView {

    void onFile(String error);
    void initShare(ImageBean imageBean, SHARE_MEDIA share_media);

    void onSuccess(NewFreeBean bean);
    void onCommenFile(String error);
}
