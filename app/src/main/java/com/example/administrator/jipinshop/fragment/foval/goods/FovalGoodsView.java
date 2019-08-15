package com.example.administrator.jipinshop.fragment.foval.goods;

import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;

/**
 * @author 莫小婷
 * @create 2019/8/15
 * @Describe
 */
public interface FovalGoodsView {
    void Success(SreachResultGoodsBean resultGoodsBean);
    void Faile(String error);
}
