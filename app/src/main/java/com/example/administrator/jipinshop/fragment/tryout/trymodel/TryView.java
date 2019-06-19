package com.example.administrator.jipinshop.fragment.tryout.trymodel;

import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TryBean;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe
 */
public interface TryView {
    void onSuccess(TryBean bean);
    void onFile(String error);

    void onSucAdList(SucBean<EvaluationTabBean.DataBean.AdListBean> adListBeanSucBean);
    void onFileAdList(String error);
}
