package com.example.administrator.jipinshop.activity.mall.exchange;

import com.example.administrator.jipinshop.bean.DefaultAddressBean;

/**
 * @author 莫小婷
 * @create 2019/3/14
 * @Describe
 */
public interface ExchangeView {

    void onSuccess(DefaultAddressBean bean);
    void onFile(String error);

    void onExchangeSuc();
    void onExchangeFile(String error);
}
