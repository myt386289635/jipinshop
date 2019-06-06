package com.example.administrator.jipinshop.activity.balance;

import com.example.administrator.jipinshop.bean.MyWalletBean;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public interface MyWalletView {

    void onSuccess(MyWalletBean bean);
    void onFile(String error);
}
