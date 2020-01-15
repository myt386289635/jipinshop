package com.example.administrator.jipinshop.activity.sreach;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2019/12/2
 * @Describe
 */
public interface TBSreachView {

    /**
     * @param content 点击位置的内容
     */
    void jump(String content);

    void Success(SreachHistoryBean sreachHistoryBean);
    void onFaile(String error);

    void SuccessDeleteAll(SuccessBean successBean);

    void SuccessHistory(SreachHistoryBean sreachHistoryBean);

    void LikeSuccess(SimilerGoodsBean bean);

    void keyShow();
    void keyHint();

    void onShareSuc(ImageBean bean);
    void onShareFile();
}
