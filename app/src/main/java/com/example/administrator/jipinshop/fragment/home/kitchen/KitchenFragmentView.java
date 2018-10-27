package com.example.administrator.jipinshop.fragment.home.kitchen;

import com.example.administrator.jipinshop.bean.KitchenFragmentBean;

/**
 * @author 莫小婷
 * @create 2018/10/27
 * @Describe
 */
public interface KitchenFragmentView {

    void onSuccess(KitchenFragmentBean recommendFragmentBean);
    void onFile(String error);
}
