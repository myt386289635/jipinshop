package com.example.administrator.jipinshop.fragment.tryout.hot;

import com.example.administrator.jipinshop.bean.TryReportBean;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe
 */
public interface TryCommenView {
    void onSuccess(TryReportBean bean);
    void onFile(String error);
}
