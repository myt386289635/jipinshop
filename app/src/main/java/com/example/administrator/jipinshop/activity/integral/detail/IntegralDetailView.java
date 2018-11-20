package com.example.administrator.jipinshop.activity.integral.detail;

import com.example.administrator.jipinshop.bean.PointDetailBean;

/**
 * @author 莫小婷
 * @create 2018/11/20
 * @Describe
 */
public interface IntegralDetailView {
    void onSuccess(PointDetailBean bean);
    void onFile(String error);
}
