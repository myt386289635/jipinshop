package com.example.administrator.jipinshop.activity.order;

import com.example.administrator.jipinshop.bean.MyOrderBean;

/**
 * @author 莫小婷
 * @create 2019/3/11
 * @Describe
 */
public interface MyOrderView {
    void Success(MyOrderBean myOrderBean);
    void Faile(String error);

    void SuccessConfirm(int position);
    void FaileConfirm(String error);
}
