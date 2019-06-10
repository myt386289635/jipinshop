package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe 提现成功后需要刷新我的钱包页面
 */
public class WithdrawBus {

    String money;

    public WithdrawBus(String money) {
        this.money = money;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
