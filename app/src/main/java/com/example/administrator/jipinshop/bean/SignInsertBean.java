package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignInsertBean {

    private String msg;
    private int code;
    private int daysCount;
    private int addPoint;
    private int needPoint;
    private int point;
    private List<DailyTaskBean.DataBean.SigninListBean> data;

    public int getNeedPoint() {
        return needPoint;
    }

    public void setNeedPoint(int needPoint) {
        this.needPoint = needPoint;
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

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public int getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(int addPoint) {
        this.addPoint = addPoint;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<DailyTaskBean.DataBean.SigninListBean> getData() {
        return data;
    }

    public void setData(List<DailyTaskBean.DataBean.SigninListBean> data) {
        this.data = data;
    }
}
