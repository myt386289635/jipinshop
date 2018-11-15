package com.example.administrator.jipinshop.fragment.evaluation;

import com.example.administrator.jipinshop.bean.EvaluationTabBean;

/**
 * @author 莫小婷
 * @create 2018/11/15
 * @Describe
 */
public interface EvaluationView {

    void onSucTab(EvaluationTabBean bean);
    void onFaile(String error);
}
