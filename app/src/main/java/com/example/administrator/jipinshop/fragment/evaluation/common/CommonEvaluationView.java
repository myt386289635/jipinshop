package com.example.administrator.jipinshop.fragment.evaluation.common;

import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/9/13
 * @Describe
 */
public interface CommonEvaluationView {

    void onSuccess(EvaluationListBean evaluationListBean);
    void onFile(String error);
}
