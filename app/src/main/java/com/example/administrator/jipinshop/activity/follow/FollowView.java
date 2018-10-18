package com.example.administrator.jipinshop.activity.follow;

import android.widget.TextView;

import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public interface FollowView {

    void FollowSuccess(FollowBean followBean);

    void FollowFaileNet(String throwable);

    void FollowFaileCode(String error);

    void ConcerDelSuccess(SuccessBean successBean, TextView textView, int pos);

}
