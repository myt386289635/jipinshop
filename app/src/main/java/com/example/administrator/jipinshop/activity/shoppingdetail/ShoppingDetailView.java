package com.example.administrator.jipinshop.activity.shoppingdetail;

import com.example.administrator.jipinshop.bean.ShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe
 */
public interface ShoppingDetailView {
     void keyShow();
     void keyHint();

     void onSuccess(ShoppingDetailBean recommendFragmentBean);
     void onFile(String error);
}
