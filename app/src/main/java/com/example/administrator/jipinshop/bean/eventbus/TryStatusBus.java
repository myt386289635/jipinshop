package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/3/27
 * @Describe 试用商品的状态改变（倒计时结束）
 */
public class TryStatusBus {

    private int pos;//位置，哪一条商品结束了

    public TryStatusBus(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
