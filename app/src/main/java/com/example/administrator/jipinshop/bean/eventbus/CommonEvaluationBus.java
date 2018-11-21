package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/21
 * @Describe 在登陆时或者退出登陆时需要刷新评测首页
 */
public class CommonEvaluationBus {

    private String refersh;

    public CommonEvaluationBus(String refersh) {
        this.refersh = refersh;
    }

    public String getRefersh() {
        return refersh;
    }

    public void setRefersh(String refersh) {
        this.refersh = refersh;
    }
}
