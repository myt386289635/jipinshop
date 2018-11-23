package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/23
 * @Describe
 */
public class MessageMsgBus {

    private String msg;
    private int position;

    public MessageMsgBus(String msg, int position) {
        this.msg = msg;
        this.position = position;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
