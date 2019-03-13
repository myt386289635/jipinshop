package com.example.administrator.jipinshop.activity.mall.detail;

import com.example.administrator.jipinshop.bean.MallDetailBean;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe
 */
public interface MallDetailView {
    void onSuccess(MallDetailBean mallDetailBean);
    void onFile(String error);
}
