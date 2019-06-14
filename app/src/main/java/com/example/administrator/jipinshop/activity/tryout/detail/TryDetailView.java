package com.example.administrator.jipinshop.activity.tryout.detail;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.TryApplyBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;

/**
 * @author 莫小婷
 * @create 2019/3/22
 * @Describe
 */
public interface TryDetailView {

    void onSuccess(TryDetailBean bean);
    void onFile(String error);

    void onSuccessApply(TryApplyBean bean);
    void onFileApply(String error);

    void onBuyLinkSuccess(ImageBean bean);
}
