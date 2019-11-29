package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/11/28
 * @Describe
 */
public class TBShopDetailBus {

    public static final String finish = "finish";

    private String tag;

    public TBShopDetailBus(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
