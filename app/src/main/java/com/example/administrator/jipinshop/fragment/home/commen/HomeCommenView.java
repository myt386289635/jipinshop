package com.example.administrator.jipinshop.fragment.home.commen;

import com.example.administrator.jipinshop.bean.HomeCommenBean;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public interface HomeCommenView {

    void onSuccess(HomeCommenBean commenBean);
    void onFile(String error);
}
