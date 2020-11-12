package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/3/27
 * @Describe 试用商品的状态改变（倒计时结束）
 */
public class HomeRefresh {

   public static final String tag = "KTMain2Fragment_Refresh";

   private String flag;

    public HomeRefresh(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
