package com.example.administrator.jipinshop.fragment.home.electricity;

import com.example.administrator.jipinshop.bean.ElectricityFragmentBean;

/**
 * @author 莫小婷
 * @create 2018/10/27
 * @Describe
 */
public interface ElectricityFragmentView {
    void onSuccess(ElectricityFragmentBean electricityFragmentBean);
    void onFile(String error);
}
