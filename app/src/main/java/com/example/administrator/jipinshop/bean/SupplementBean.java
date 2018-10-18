package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/15
 * @Describe
 */
public class SupplementBean {


    /**
     * msg : 补签成功
     * score : 1224
     * code : 200
     */

    private String msg;
    private String score;
    private int code;
    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
