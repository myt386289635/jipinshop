package com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail;

import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
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

    void onBuyLinkSuccess(ClickUrlBean bean);

    void onDescImgs(SucBean<String> bean);

    void onCollect(TBShoppingDetailBean bean);

    void onCreateGroup();
    void onGroupDialogSuc(PopBean bean);
    void initShare(ShareInfoBean bean);
}
