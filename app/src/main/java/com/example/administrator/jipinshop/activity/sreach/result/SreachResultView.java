package com.example.administrator.jipinshop.activity.sreach.result;

import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public interface SreachResultView {
    /**
     * @param from 来自热门搜索 2，还是 历史搜索 1
     * @param content 点击位置的内容
     */
    void onResher(String from,String content);

    void Success(SreachResultGoodsBean resultBean);

    void Faile(String error);

}
