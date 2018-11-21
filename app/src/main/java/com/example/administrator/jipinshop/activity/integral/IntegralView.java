package com.example.administrator.jipinshop.activity.integral;

import com.example.administrator.jipinshop.bean.IntegralShopBean;

/**
 * @author 莫小婷
 * @create 2018/11/21
 * @Describe
 */
public interface IntegralView {
    void onSuccess(IntegralShopBean shopBean);
    void onFile(String error);
}
