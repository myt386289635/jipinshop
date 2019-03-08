package com.example.administrator.jipinshop.activity.address;

import com.example.administrator.jipinshop.bean.AddressBean;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe
 */
public interface MyAddressView {
    void onSuccess(AddressBean addressBean);
    void onFile(String error);

    void onSuccessDelete(int position);
    void onFileDelete(String error);

    void onSuccessDefault(int position);
}
