package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe 试用报告详情页  评论刷新
 */
public class TryBus {
    private int count;//评论总数

    public TryBus(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
