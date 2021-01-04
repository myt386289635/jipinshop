package com.example.administrator.jipinshop.activity.home.seckill.detail;

import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;

/**
 * @author 莫小婷
 * @create 2021/1/4
 * @Describe
 */
public interface SeckillDetailView {

    void LikeSuccess(SimilerGoodsBean bean);
    void onFile(String error);
    void onSuccess(TBShoppingDetailBean bean);
    void onCollect(TBShoppingDetailBean bean);

    void onDescImgs(SucBean<String> bean);
    void onBuyLinkSuccess(ClickUrlBean bean);
    void onCreateGroup();
    void onSucCollectInsert();
    void onSucCollectDelete();
    void onGroupDialogSuc(PopBean bean);
    void initShare(ShareInfoBean bean);
}
