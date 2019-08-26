package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe 未读消息
 */
public class UnMessageBean {

    /**
     * msg : success
     * code : 0
     * data : 0
     */

    private String msg;
    private int code;
    private int data;
    private int walletCount;

    public int getWalletCount() {
        return walletCount;
    }

    public void setWalletCount(int walletCount) {
        this.walletCount = walletCount;
    }

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
