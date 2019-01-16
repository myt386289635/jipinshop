package com.example.administrator.jipinshop.fragment.follow.attention;

import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2019/1/15
 * @Describe
 */
public interface AttentionView {

    void FollowSuccess(FollowBean followBean);

    void FollowFaileCode(String error);

    void ConcerDelSuccess(SuccessBean successBean, int pos);
    void ConcerDelFaile(String error);

    void concerInsSuccess(SuccessBean successBean, int pos);
}
