package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/16
 * @Describe 评测刷新评论数量的
 */
public class EvaluationBus {

    private String id;
    private int count;//评论总数

    public EvaluationBus(String id, int count) {
        this.id = id;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
