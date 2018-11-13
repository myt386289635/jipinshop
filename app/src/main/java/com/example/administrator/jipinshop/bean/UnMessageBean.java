package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe 未读消息
 */
public class UnMessageBean {

    /**
     * msg : success
     * code : 200
     * count : 6
     */

    private String msg;
    private String code;
    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
