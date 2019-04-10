package com.example.administrator.jipinshop.fragment.tryout.mine;

import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.TrialCommonBean;

/**
 * @author 莫小婷
 * @create 2019/4/8
 * @Describe
 */
public interface TrialCommonView {
    void onSuccess(TrialCommonBean bean);
    void onFile(String error);

    void onAddressSuccess(DefaultAddressBean bean);
    void onAddressFile(String error);

    void onConfirmSuccess();
    void onConfirmFile(String error);
}
