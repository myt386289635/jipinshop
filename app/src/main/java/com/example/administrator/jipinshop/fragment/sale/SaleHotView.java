package com.example.administrator.jipinshop.fragment.sale;

import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBCategoryBean;

/**
 * @author 莫小婷
 * @create 2021/4/25
 * @Describe
 */
public interface SaleHotView {

    void refresh();
    void onSuccess(TBCategoryBean bean);
    void onFile(String error);

    void onGoodsList(SimilerGoodsBean bean);
    void onGoodsFile(String error);
}
