package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/1/10
 * @Describe
 */
public class SucBeanT<T> {

    private String msg;
    private int code;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
