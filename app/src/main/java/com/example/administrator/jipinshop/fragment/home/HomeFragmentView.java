package com.example.administrator.jipinshop.fragment.home;

import com.example.administrator.jipinshop.bean.TabBean;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe
 */
public interface HomeFragmentView {

    void Success(TabBean tabBean);
    void Faile(String error);
}
