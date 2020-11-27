package com.example.administrator.jipinshop.activity.mine.group;

import com.example.administrator.jipinshop.bean.GroupInfoBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;

/**
 * @author 莫小婷
 * @create 2020/11/25
 * @Describe
 */
public interface MyGroupView {

    void LikeSuccess(SimilerGoodsBean bean);
    void onFaile(String error);

    void onSuccess(GroupInfoBean bean);

    void initShare(ShareInfoBean bean);
}
