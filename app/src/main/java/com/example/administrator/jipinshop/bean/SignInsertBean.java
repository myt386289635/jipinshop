package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignInsertBean {

    /**
     * msg : success
     * code : 200
     * signinInfo : {"id":"1eb046f8760c48fb9f4bf3016cc8b556","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","week":"0,1,0,0,0,0,0","weekArray":["0","1","0","0","0","0","0"],"lastTime":"2018-11-20 16:11:14","daysCount":1,"remark":"签到"}
     * points : 2
     */

    private String msg;
    private int code;
    private int points;
    private int addPoint;

    public int getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(int addPoint) {
        this.addPoint = addPoint;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
