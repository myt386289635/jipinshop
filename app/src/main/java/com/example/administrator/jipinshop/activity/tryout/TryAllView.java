package com.example.administrator.jipinshop.activity.tryout;

import com.example.administrator.jipinshop.bean.TryAllBean;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe
 */
public interface TryAllView {
    void onSuccess(TryAllBean bean);
    void onFile(String error);
}
