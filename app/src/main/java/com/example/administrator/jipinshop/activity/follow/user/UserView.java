package com.example.administrator.jipinshop.activity.follow.user;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.UserPageBean;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe
 */
public interface UserView {
    void onSuccess(UserPageBean userPageBean);
    void onFile(String error);

    void ConcerDelSuccess(SuccessBean successBean);
    void ConcerDelFaile(String error);

    void concerInsSuccess(SuccessBean successBean);
}
