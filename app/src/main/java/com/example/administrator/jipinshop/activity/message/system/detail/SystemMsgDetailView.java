package com.example.administrator.jipinshop.activity.message.system.detail;

import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe
 */
public interface SystemMsgDetailView {
    void onSuccess(SuccessBean successBean);
    void onFaile(String error);
}
