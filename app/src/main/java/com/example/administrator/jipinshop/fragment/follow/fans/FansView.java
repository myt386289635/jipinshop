package com.example.administrator.jipinshop.fragment.follow.fans;

import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2019/1/16
 * @Describe
 */
public interface FansView {

    void FollowSuccess(FollowBean followBean);

    void FollowFaileCode(String error);

    void ConcerDelSuccess(SuccessBean successBean, int pos);
    void ConcerDelFaile(String error);

    void concerInsSuccess(SuccessBean successBean, int pos);
}
