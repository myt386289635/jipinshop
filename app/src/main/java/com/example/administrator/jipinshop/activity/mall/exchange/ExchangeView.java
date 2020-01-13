package com.example.administrator.jipinshop.activity.mall.exchange;

import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;

/**
 * @author 莫小婷
 * @create 2019/3/14
 * @Describe
 */
public interface ExchangeView {

    void onSuccess(DefaultAddressBean bean);
    void onFile(String error);

    void onExchangeSuc(MyOrderBean.DataBean bean);
    void onExchangeFile(String error);
}
