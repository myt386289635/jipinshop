package com.example.administrator.jipinshop.fragment.home.recommend;

import com.example.administrator.jipinshop.bean.RecommendFragmentBean;

/**
 * @author 莫小婷
 * @create 2018/8/16
 * @Describe
 */
public interface RecommendFragmentView {

    void onSuccess(RecommendFragmentBean recommendFragmentBean);
    void onFile(String error);
}
