package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 每日任务返回后需要改变首页页面位置
 */
public class ChangeHomePageBus {

    private int page;

    public ChangeHomePageBus(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
