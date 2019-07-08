package com.example.administrator.jipinshop.activity.home.classification;

import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe
 */
public interface ClassifyView {

    void onSuccess(TopCategoryDetailBean bean);
    void onFile(String error);

    void onSuccessSed(SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> bean);
    void onFlieSed(String error);
}
