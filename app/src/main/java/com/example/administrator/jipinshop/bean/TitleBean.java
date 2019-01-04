package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/1/4
 * @Describe
 */
public class TitleBean {
    private String mString;
    private Boolean tag;

    public TitleBean(String string, Boolean tag) {
        mString = string;
        this.tag = tag;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }
}
