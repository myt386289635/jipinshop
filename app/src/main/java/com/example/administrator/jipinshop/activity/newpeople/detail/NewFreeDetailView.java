package com.example.administrator.jipinshop.activity.newpeople.detail;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2020/5/25
 * @Describe
 */
public interface NewFreeDetailView {
    void onFile(String error);

    void LikeSuccess(SimilerGoodsBean bean);
    void onSuccess(TBShoppingDetailBean bean);

    void onBuySuccess(ImageBean successBean);
}
