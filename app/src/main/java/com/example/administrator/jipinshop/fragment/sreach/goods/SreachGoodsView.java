package com.example.administrator.jipinshop.fragment.sreach.goods;

import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public interface SreachGoodsView {

    void Success(SreachResultGoodsBean resultGoodsBean);
    void Faile(String error);
}
