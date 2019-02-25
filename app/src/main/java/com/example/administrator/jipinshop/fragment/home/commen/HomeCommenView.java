package com.example.administrator.jipinshop.fragment.home.commen;

import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.OrderbyTypeBean;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public interface HomeCommenView {

    void onSuccess(HomeCommenBean commenBean);
    void onFile(String error);

    void onSuccessTab(OrderbyTypeBean commenBean);
    void onFileTab(String error);
}
