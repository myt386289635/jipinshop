package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe 刷新发现详情评论条数
 */
public class FindBus {

    private int count;//评论总数

    public FindBus(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
