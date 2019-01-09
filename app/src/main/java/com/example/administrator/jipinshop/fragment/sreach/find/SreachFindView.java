package com.example.administrator.jipinshop.fragment.sreach.find;

import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public interface SreachFindView {

    void Success(SreachResultArticlesBean articlesBean);
    void Faile(String error);
}
