package com.example.administrator.jipinshop.activity.newpeople;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2020/5/22
 * @Describe
 */
public interface NewFreeView {

    void onSuccess(NewFreeBean bean);
    void onFile(String error);

    void onShareSuc(ImageBean imageBean, SHARE_MEDIA share_media);
    void onCommenFile(String error);
}
