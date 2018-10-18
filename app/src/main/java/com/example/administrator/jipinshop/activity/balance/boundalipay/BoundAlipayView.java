package com.example.administrator.jipinshop.activity.balance.boundalipay;

import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe
 */
public interface BoundAlipayView {

    void timerEnd();

    void success(SuccessBean bean);
}
