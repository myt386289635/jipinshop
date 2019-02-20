package com.example.administrator.jipinshop.fragment.home.recommend.tabitem;

import com.example.administrator.jipinshop.bean.HomeCommenBean;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe
 */
public interface TabCommenView {
    void onSuccess(HomeCommenBean commenBean);
    void onFile(String error);
}
