package com.example.administrator.jipinshop.activity.setting.about.cancellation;

import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2020/5/18
 * @Describe
 */
public interface VerificationView {
    void timerEnd();
    void onSuccess(SuccessBean bean);
}
