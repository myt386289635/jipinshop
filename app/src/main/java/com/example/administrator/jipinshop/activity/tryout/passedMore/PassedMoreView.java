package com.example.administrator.jipinshop.activity.tryout.passedMore;

import com.example.administrator.jipinshop.bean.PassedMoreBean;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe
 */
public interface PassedMoreView {

    void onSuccess(PassedMoreBean bean);
    void onFaile(String error);
}
