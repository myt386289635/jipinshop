package com.example.administrator.jipinshop.activity.sreach;

import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public interface SreachView {

    /**
     * @param from
     * @param content 点击位置的内容
     * @param position   位置
     */
    void jump(String from,String content,int position);

    void Success(SreachHistoryBean sreachHistoryBean);
    void onFaile(String error);

    void SuccessDeleteAll(SuccessBean successBean);

    void SuccessDelete(int position,SuccessBean successBean);

    void SuccessHistory(SreachHistoryBean sreachHistoryBean);
}
