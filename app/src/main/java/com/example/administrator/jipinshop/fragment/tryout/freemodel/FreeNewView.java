package com.example.administrator.jipinshop.fragment.tryout.freemodel;

import com.example.administrator.jipinshop.bean.V2FreeListBean;

/**
 * @author 莫小婷
 * @create 2019/11/12
 * @Describe
 */
public interface FreeNewView {

    void onSuccess(V2FreeListBean bean);
    void onFile(String error);
}
