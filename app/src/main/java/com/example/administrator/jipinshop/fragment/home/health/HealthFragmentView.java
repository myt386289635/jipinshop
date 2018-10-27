package com.example.administrator.jipinshop.fragment.home.health;

import com.example.administrator.jipinshop.bean.HealthFragmentBean;

/**
 * @author 莫小婷
 * @create 2018/10/26
 * @Describe
 */
public interface HealthFragmentView {

    void onSuccess(HealthFragmentBean recommendFragmentBean);
    void onFile(String error);
}
