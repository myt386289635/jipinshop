package com.example.administrator.jipinshop.activity.home.classification.article;

import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;

/**
 * @author 莫小婷
 * @create 2019/7/8
 * @Describe
 */
public interface ArticleMoreView {

    void onSuccess(SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean> bean);
    void onFile(String error);
}
