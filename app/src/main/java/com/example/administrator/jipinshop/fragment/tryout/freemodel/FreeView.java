package com.example.administrator.jipinshop.fragment.tryout.freemodel;

import com.example.administrator.jipinshop.bean.FreeBean;

/**
 * @author 莫小婷
 * @create 2019/6/21
 * @Describe
 */
public interface FreeView {

    void onSuccess(FreeBean bean);
    void onFile(String error);

}
