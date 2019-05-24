package com.example.administrator.jipinshop.fragment.tryout.mine;

import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;

/**
 * @author 莫小婷
 * @create 2019/5/23
 * @Describe
 */
public interface TrialReportView {

    void Success(SreachResultArticlesBean articlesBean);
    void Faile(String error);
}
