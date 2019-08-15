package com.example.administrator.jipinshop.fragment.sreach.goods;

import com.example.administrator.jipinshop.bean.SreachBean;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public interface SreachGoodsView {

    void Success(SreachBean resultGoodsBean);
    void Faile(String error);
}
