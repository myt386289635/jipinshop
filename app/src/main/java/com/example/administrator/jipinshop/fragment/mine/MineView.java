package com.example.administrator.jipinshop.fragment.mine;

import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;

/**
 * @author 莫小婷
 * @create 2018/10/9
 * @Describe
 */
public interface MineView {

    void successMoney(AccountBean accountBean);

    void successUserInfo(UserInfoBean userInfoBean);

    void FaileUserInfo(String error);
}
