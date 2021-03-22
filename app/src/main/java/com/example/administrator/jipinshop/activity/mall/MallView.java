package com.example.administrator.jipinshop.activity.mall;

import com.example.administrator.jipinshop.bean.MallBean;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe
 */
public interface MallView {
    void onSuccess(MallBean bean);
    void onFile(String error);

    void onHot(MallBean bean);
    void onHotFile(String error);
}
