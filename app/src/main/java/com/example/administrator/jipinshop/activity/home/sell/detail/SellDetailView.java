package com.example.administrator.jipinshop.activity.home.sell.detail;

import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.CommenBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2021/4/28
 * @Describe
 */
public interface SellDetailView {

    void onSuccess(TBShoppingDetailBean bean);
    void onFile(String error);
    void onCollect(TBShoppingDetailBean bean);
    void LikeSuccess(SimilerGoodsBean bean);
    void recommend(SimilerGoodsBean bean);
    void onComment(CommenBean bean);

    void onDescImgs(SucBean<String> bean);
    void onBuyLinkSuccess(ClickUrlBean bean);
    void onSucCollectInsert();
    void onSucCollectDelete();
    void onShop(ImageBean bean);
    void onPop(PopBean bean);
    void initShare(ShareInfoBean bean);
}
