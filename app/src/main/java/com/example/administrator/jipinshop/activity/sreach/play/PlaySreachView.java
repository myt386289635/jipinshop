package com.example.administrator.jipinshop.activity.sreach.play;

import com.example.administrator.jipinshop.bean.SreachHistoryBean;

/**
 * @author 莫小婷
 * @create 2020/11/11
 * @Describe
 */
public interface PlaySreachView {

    /**
     * @param content 点击位置的内容
     * @param position   位置
     */
    void jump(String content,int position);

    void Success(SreachHistoryBean sreachHistoryBean);
    void onFaile(String error);
}
