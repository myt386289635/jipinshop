package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2018/11/21
 * @Describe 在登陆时需要刷新商品详情、发现、评测详情、评论列表
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
