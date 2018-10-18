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

    public EditNameBus(String tag, String totleMoney, String state, String unUseMoney, String useMoney, String none) {
        this.tag = tag;
        this.totleMoney = totleMoney;
        this.state = state;
        this.unUseMoney = unUseMoney;
        this.useMoney = useMoney;
        this.none = none;
    }


    private String totleMoney;//总金额
    private String state;//处理中
    private String unUseMoney;//可提现
    private String useMoney;//已提现
    private String none;//待结算

    public String getTotleMoney() {
        return totleMoney;
    }

    public void setTotleMoney(String totleMoney) {
        this.totleMoney = totleMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUnUseMoney() {
        return unUseMoney;
    }

    public void setUnUseMoney(String unUseMoney) {
        this.unUseMoney = unUseMoney;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getNone() {
        return none;
    }

    public void setNone(String none) {
        this.none = none;
    }


    /***************签到时候返回的积分*******************/

    public EditNameBus(String tag) {
        this.tag = tag;
    }
}
