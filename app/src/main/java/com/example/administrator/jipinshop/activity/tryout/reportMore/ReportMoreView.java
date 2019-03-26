package com.example.administrator.jipinshop.activity.tryout.reportMore;

import com.example.administrator.jipinshop.bean.TryReportBean;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe
 */
public interface ReportMoreView {
    void onSuccess(TryReportBean bean);
    void onFile(String error);
}
