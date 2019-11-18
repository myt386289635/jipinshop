package com.example.administrator.jipinshop.activity.tryout.freedetail;

import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;

/**
 * @author 莫小婷
 * @create 2019/11/13
 * @Describe
 */
public interface FreeNewDetailView {
    void onSuccess(FreeDetailBean detailBean);
    void onFile(String error);

    void onApply(ImageBean bean);

    void onPoster(ImageBean bean);
}
