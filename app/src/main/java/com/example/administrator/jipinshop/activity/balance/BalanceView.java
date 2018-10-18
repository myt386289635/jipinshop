package com.example.administrator.jipinshop.activity.balance;

import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public interface BalanceView {
    void successMoney(AccountBean accountBean);

    void alipySuccess(SuccessBean successBean);
}
