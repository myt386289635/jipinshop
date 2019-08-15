package com.example.administrator.jipinshop.fragment.foval.goods;

import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;

/**
 * @author 莫小婷
 * @create 2019/8/15
 * @Describe
 */
public interface FovalGoodsView {
    void Success(SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> resultGoodsBean);
    void Faile(String error);
}
