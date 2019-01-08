package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe 页面状态
 */
public class PagerStateBean {

    /**
     * msg : success
     * code : 0
     * follow : 0
     * collect : 1
     * vote : 1
     */

    private String msg;
    private int code;
    private int follow;
    private int collect;
    private int vote;

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

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
