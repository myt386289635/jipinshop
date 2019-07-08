package com.example.administrator.jipinshop.activity.home.newarea;

import com.example.administrator.jipinshop.bean.EvaluationListBean;

/**
 * @author 莫小婷
 * @create 2019/7/5
 * @Describe
 */
public interface NewAreaView {

    void onSuccess(EvaluationListBean bean);
    void onFlie(String error);
}
