package com.example.administrator.jipinshop.activity.balance.withdraw;

import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe
 */
public interface WithdrawView {

    void onSuccess(WithdrawBean bean);
    void onFile(String error);

    void onWithdrawSuccess();
    void onWithdrawFile(String error);

    void onSuccessAccount(TaobaoAccountBean bean);
}
