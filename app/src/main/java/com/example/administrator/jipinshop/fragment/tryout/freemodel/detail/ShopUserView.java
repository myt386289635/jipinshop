package com.example.administrator.jipinshop.fragment.tryout.freemodel.detail;

import com.example.administrator.jipinshop.bean.FreeUserListBean;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe
 */
public interface ShopUserView {

   void onSuccess(FreeUserListBean bean);
   void onFile(String error);
}
