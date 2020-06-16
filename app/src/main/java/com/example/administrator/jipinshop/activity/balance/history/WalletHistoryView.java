package com.example.administrator.jipinshop.activity.balance.history;

import com.example.administrator.jipinshop.bean.WalletHistoryBean;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe
 */
public interface WalletHistoryView {

    void onSuccess(WalletHistoryBean bean);
    void onFile(String error);
}
