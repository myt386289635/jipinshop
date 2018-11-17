package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe 加载完成发现首页tab后发送
 */
public class FindTabBus {

    private String tag;

    public FindTabBus(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
