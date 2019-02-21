package com.example.administrator.jipinshop.activity.home.tabitem;

import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe
 */
public interface ALLTabView {

    void Success(SreachResultGoodsBean resultGoodsBean);
    void Faile(String error);

    void onSuccess(HomeCommenBean commenBean);
}
