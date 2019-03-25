package com.example.administrator.jipinshop.activity.tryout.detail;

import com.example.administrator.jipinshop.bean.TryDetailBean;

/**
 * @author 莫小婷
 * @create 2019/3/22
 * @Describe
 */
public interface TryDetailView {

    void onSuccess(TryDetailBean bean);
    void onFile(String error);

    void onSuccessApply();
    void onFileApply(String error);
}
