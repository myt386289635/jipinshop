package com.example.administrator.jipinshop.activity.member.zero.detail;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public interface ZeroDetailView {

    void onFile(String error);

    void LikeSuccess(SimilerGoodsBean bean);

    void onSuccess(TBShoppingDetailBean bean);

    void onBuySuccess(ImageBean successBean);
}
