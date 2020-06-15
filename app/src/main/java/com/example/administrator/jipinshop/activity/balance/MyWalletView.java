package com.example.administrator.jipinshop.activity.balance;

import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public interface MyWalletView {

    void onSuccess(MyWalletBean bean);
    void onFile(String error);

    void onScoreSuc(ScoreStatusBean bean);
}
