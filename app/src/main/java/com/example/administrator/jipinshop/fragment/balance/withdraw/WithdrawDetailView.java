package com.example.administrator.jipinshop.fragment.balance.withdraw;

import com.example.administrator.jipinshop.bean.WithdrawDetailBean;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe
 */
public interface WithdrawDetailView {

    void onSuccess(WithdrawDetailBean bean);
    void onFile(String error);
}
