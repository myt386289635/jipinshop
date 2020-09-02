package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2020/8/31
 * @Describe 支付回调结果
 */
public class PayBus {

    private String type;
    private String flag;

    public PayBus(String type, String flag) {
        this.type = type;
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
