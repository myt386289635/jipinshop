package com.example.administrator.jipinshop.fragment.mine;

import com.example.administrator.jipinshop.bean.UserInfoBean;

/**
 * @author 莫小婷
 * @create 2018/10/9
 * @Describe
 */
public interface MineView {

    void successUserInfo(UserInfoBean userInfoBean);

    void FaileUserInfo(UserInfoBean error);

    void successUpdateInfo(UserInfoBean userInfoBean);
}
