package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe
 */
public class ImageBean {

    /**
     * msg : success
     * code : 200
     * userNickImg : http://192.168.5.182:8083/img/1539761216882aaaa.jpg
     */

    private String msg;
    private int code;
    private String userNickImg;

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

    public String getUserNickImg() {
        return userNickImg;
    }

    public void setUserNickImg(String userNickImg) {
        this.userNickImg = userNickImg;
    }
}
