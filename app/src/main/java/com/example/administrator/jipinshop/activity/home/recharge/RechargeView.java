package com.example.administrator.jipinshop.activity.home.recharge;

import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;

/**
 * @author 莫小婷
 * @create 2021/1/19
 * @Describe
 */
public interface RechargeView {

    void onSuccess(SucBean<EvaluationTabBean.DataBean.AdListBean> bean);
    void onFile(String error);

}
