package com.example.administrator.jipinshop.activity.tryout.freedetail;

import com.example.administrator.jipinshop.bean.FreeDetailBean;

/**
 * @author 莫小婷
 * @create 2019/11/13
 * @Describe
 */
public interface FreeNewDetailView {
    void onSuccess(FreeDetailBean detailBean);
    void onFile(String error);
}
