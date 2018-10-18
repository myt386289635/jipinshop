package com.example.administrator.jipinshop.fragment.evaluation.common;

import com.example.administrator.jipinshop.bean.CommonEvaluationBean;

/**
 * @author 莫小婷
 * @create 2018/9/13
 * @Describe
 */
public interface CommonEvaluationView {

    void onSuccess(CommonEvaluationBean commonEvaluationBean);
    void onFile(String error);
}
