package com.example.administrator.jipinshop.activity.info.account.change;

import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2019/2/15
 * @Describe
 */
public interface ChangePhoneView {
    void timerEnd();

    void onSuccess(SuccessBean successBean);
    void onFile(String error);
}
