package com.example.administrator.jipinshop.activity.home.seckill;

import com.example.administrator.jipinshop.bean.SeckillBean;
import com.example.administrator.jipinshop.bean.SeckillTabBean;

/**
 * @author 莫小婷
 * @create 2020/12/30
 * @Describe
 */
public interface SeckillView {

    void onTab(SeckillTabBean bean);
    void onFile(String error);
    void onSuccess(SeckillBean bean);
    void onContentFile(String error);
}
