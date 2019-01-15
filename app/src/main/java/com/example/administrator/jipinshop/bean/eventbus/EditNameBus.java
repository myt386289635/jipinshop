package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/9/30
 * @Describe
 */
public class EditNameBus {

    private String tag;

    /*******************修改的用户信息***********************/
    private String content;
    private String type;//修改的类型

    public EditNameBus(String tag, String content, String type) {
        this.tag = tag;
        this.content = content;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /*******************修改的用户信息***********************/


    /*******************登录时需要返回的信息***********************/

    public EditNameBus(String tag, String fansCount, String voteCount, String followCount) {
        this.tag = tag;
        this.fansCount = fansCount;
        this.voteCount = voteCount;
        this.followCount = followCount;
    }


    private String fansCount;//粉丝数
    private String voteCount;//点赞数
    private String followCount;//关注数

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getFansCount() {
        return fansCount;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getFollowCount() {
        return followCount;
    }

    /***************签到时候返回的积分*******************/

    public EditNameBus(String tag) {
        this.tag = tag;
    }

    /********************首页获取未读数量********************/

    private String count;

    public EditNameBus(String tag, String count) {
        this.tag = tag;
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
