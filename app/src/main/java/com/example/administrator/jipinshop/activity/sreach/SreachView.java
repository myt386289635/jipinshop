package com.example.administrator.jipinshop.activity.sreach;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public interface SreachView {

    /**
     * @param from 来自热门搜索 2，还是 历史搜索 1
     * @param content 点击位置的内容
     */
    void jump(String from,String content);

}
