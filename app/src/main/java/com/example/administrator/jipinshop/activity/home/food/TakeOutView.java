package com.example.administrator.jipinshop.activity.home.food;

import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;

/**
 * @author 莫小婷
 * @create 2020/11/26
 * @Describe
 */
public interface TakeOutView {

    void onSuccess(SucBean<EvaluationTabBean.DataBean.AdListBean> bean);
    void onFile(String error);
}
