package com.example.administrator.jipinshop.fragment.home.commen.tabitem;

import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe
 */
public interface ItemTabCommenView {

    void Success(SreachResultGoodsBean resultGoodsBean);
    void Faile(String error);
}
