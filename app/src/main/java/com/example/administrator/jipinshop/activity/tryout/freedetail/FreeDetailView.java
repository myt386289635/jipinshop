package com.example.administrator.jipinshop.activity.tryout.freedetail;

import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;

/**
 * @author 莫小婷
 * @create 2019/6/21
 * @Describe
 */
public interface FreeDetailView {

    void onSuccess(FreeDetailBean detailBean);
    void onFile(String error);

    void onBuyLinkSuccess(ImageBean bean);

    void onApplySuccess(FreeDetailBean detailBean);
}
