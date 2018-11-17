package com.example.administrator.jipinshop.fragment.find.common;

import com.example.administrator.jipinshop.bean.FindListBean;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe
 */
public interface CommonFindView {

    void onSuccess(FindListBean findListBean);
    void onFile(String error);
}
