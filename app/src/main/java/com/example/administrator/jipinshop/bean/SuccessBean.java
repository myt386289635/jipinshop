package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/9/27
 * @Describe
 */
public class SuccessBean {

    /**
     * msg : success
     * code : 200
     */
    private String msg;
    private int code;

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


    @Override
    public String toString() {
        return "SuccessBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
