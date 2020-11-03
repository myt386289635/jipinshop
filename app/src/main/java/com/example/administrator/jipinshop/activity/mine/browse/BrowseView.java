package com.example.administrator.jipinshop.activity.mine.browse;

import com.example.administrator.jipinshop.bean.TBSreachResultBean;

/**
 * @author 莫小婷
 * @create 2020/10/30
 * @Describe
 */
public interface BrowseView {

    void onSuccess(TBSreachResultBean resultGoodsBean);
    void onFaile(String error);

}
