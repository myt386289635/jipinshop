package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignInsertBean {

    private String msg;
    private int code;
    private String totalPoints;

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
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
}
