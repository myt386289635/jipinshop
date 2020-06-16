package com.example.administrator.jipinshop.activity.balance.detail;

import com.example.administrator.jipinshop.bean.CommssionDetailBean;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe
 */
public interface WalletDetailView {

    void onSuccess(CommssionDetailBean bean);
    void onFile(String error);
}
