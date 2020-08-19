package com.example.administrator.jipinshop.activity.newpeople.cheap;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2020/3/5
 * @Describe
 */
public interface CheapBuyDetailView {
    void onSuccess(TBShoppingDetailBean bean);
    void onFile(String error);

    void LikeSuccess(SimilerGoodsBean bean);

    void onBuySuccess(ImageBean successBean);

    void onIsNewUser(TBShoppingDetailBean bean);
}
