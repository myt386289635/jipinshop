package com.example.administrator.jipinshop.fragment.home;

import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TopCategorysListBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;

/**
 * @author 莫小婷
 * @create 2019/6/27
 * @Describe
 */
public interface HomeNewView {

    void Success(TabBean tabBean);
    void Faile(String error);

    void SuccessList(TopCategorysListBean bean);
    void FaileList(String error);
}
