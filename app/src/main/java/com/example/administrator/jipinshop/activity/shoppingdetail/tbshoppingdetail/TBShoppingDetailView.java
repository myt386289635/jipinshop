package com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2019/11/28
 * @Describe
 */
public interface TBShoppingDetailView {
    void onSuccess(TBShoppingDetailBean bean);
    void onFile(String error);

    void LikeSuccess(SimilerGoodsBean bean);

    void onSucCollectInsert();
    void onSucCollectDelete();

    void onBuyLinkSuccess(ImageBean bean);

    void onDescImgs(SucBean<String> bean);

    void onShareSuc(ImageBean bean,String path , String shareName);
    void onShareFile(String path , String shareName, String shareImage);
}
