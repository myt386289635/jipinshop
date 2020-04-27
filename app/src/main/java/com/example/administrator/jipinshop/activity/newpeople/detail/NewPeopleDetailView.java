package com.example.administrator.jipinshop.activity.newpeople.detail;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2020/3/5
 * @Describe
 */
public interface NewPeopleDetailView {
    void onSuccess(TBShoppingDetailBean bean);
    void onFile(String error);

    void LikeSuccess(SimilerGoodsBean bean);

    void onBuySuccess(ImageBean successBean);

    void onDescImgs(SucBean<String> bean);

    void onIsNewUser(TBShoppingDetailBean bean);
}
