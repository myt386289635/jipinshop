package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/15
 * @Describe
 */
public class SupplementBean {

    /**
     * msg : success
     * code : 200
     * supplementDays : 1
     * point : -10
     * points : 992
     */

    private String msg;
    private int code;
    private int supplementDays;
    private int point;
    private int points;

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

    public int getSupplementDays() {
        return supplementDays;
    }

    public void setSupplementDays(int supplementDays) {
        this.supplementDays = supplementDays;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
