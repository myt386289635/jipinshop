package com.example.administrator.jipinshop.activity.home.classification.questions.detail;

import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.bean.SucBean;

/**
 * @author 莫小婷
 * @create 2019/7/8
 * @Describe
 */
public interface QuestionDetailView {

    void keyShow();
    void keyHint();

    void onSuccess(SucBean<QuestionsBean.DataBean.AnswerBean> bean);
    void onFile(String error);

    void onSuccessComment();
    void onFileComment(String error);
}
