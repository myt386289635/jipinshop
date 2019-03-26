package com.example.administrator.jipinshop.activity.tryout.shareMore;

import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe
 */
public interface ShareMoreView {
    void onSuccess(SucBean<TryDetailBean.DataBean.ApplyUserListBean> bean);
    void onFile(String error);
}
