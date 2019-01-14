package com.example.administrator.jipinshop.activity.message.detail;

import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe
 */
public interface MsgDetailView {
    void onSuccess(SuccessBean successBean);
    void onFaile(String error);
}
