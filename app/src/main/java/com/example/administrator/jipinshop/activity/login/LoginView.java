package com.example.administrator.jipinshop.activity.login;

import com.example.administrator.jipinshop.bean.LoginBean;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public interface LoginView {

    void timerEnd();

    void loginSuccess(LoginBean successBean);
}
