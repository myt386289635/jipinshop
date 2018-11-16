package com.example.administrator.jipinshop.activity.follow;

import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public interface FollowView {

    void FollowSuccess(FollowBean followBean);

    void FollowFaileCode(String error);

    void ConcerDelSuccess(SuccessBean successBean, int pos);
    void ConcerDelFaile(String error);

    void concerInsSuccess(SuccessBean successBean, int pos);
}
