package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/3/25
 * @Describe
 */
public class TryShopBus {
    private int count;//评论总数

    public TryShopBus(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
