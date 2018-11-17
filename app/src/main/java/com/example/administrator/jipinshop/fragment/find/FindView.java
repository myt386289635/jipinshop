package com.example.administrator.jipinshop.fragment.find;

import com.example.administrator.jipinshop.bean.EvaluationTabBean;

/**
 * @author 莫小婷
 * @create 2018/11/16
 * @Describe
 */
public interface FindView {
    void onSucTab(EvaluationTabBean bean);
    void onFaile(String error);
}
