package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe 关注刷新
 */
public class ConcerBus {

    private String mString;//标示

    private int concerNum;//是否关注
    private String fansNum;//粉丝数
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ConcerBus(String string, int concerNum, String fansNum,String userId) {
        mString = string;
        this.concerNum = concerNum;
        this.fansNum = fansNum;
        this.userId = userId;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public int getConcerNum() {
        return concerNum;
    }

    public void setConcerNum(int concerNum) {
        this.concerNum = concerNum;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }
}
