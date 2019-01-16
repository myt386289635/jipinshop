package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/20
 * @Describe 个人主页取消关注时需要刷新关注列表
 */
public class FollowBus {

    private String tag;
    private int count;

    public FollowBus(String tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FollowBus(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
