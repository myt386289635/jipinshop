package com.example.administrator.jipinshop.activity.home.classification.questions;

import com.example.administrator.jipinshop.bean.QuestionsBean;

/**
 * @author 莫小婷
 * @create 2019/7/5
 * @Describe
 */
public interface QuestionsView {

    void onSuccess(QuestionsBean bean);
    void onFile(String error);
}
