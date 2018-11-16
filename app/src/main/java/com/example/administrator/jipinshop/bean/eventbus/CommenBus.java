package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/16
 * @Describe 刷新商品详情中的评论显示
 */
public class CommenBus {

    private String tag ;

    public CommenBus(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
