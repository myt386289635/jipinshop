package com.example.administrator.jipinshop.activity.sreach.play.result;

import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TbkIndexBean;

/**
 * @author 莫小婷
 * @create 2020/11/12
 * @Describe
 */
public interface PlaySreachResultView {

    void onSuccess(SucBean<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean> bean);
    void onFile(String error);
}
