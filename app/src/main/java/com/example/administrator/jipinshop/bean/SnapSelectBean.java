package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/8
 * @Describe 点赞
 */
public class SnapSelectBean {

    /**
     * msg : 未点赞
     * code : 400
     * count : 0
     */

    private String msg;
    private int code;
    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
