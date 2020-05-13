package com.example.administrator.jipinshop.activity.login.input;

import com.example.administrator.jipinshop.bean.LoginBean;

/**
 * @author 莫小婷
 * @create 2020/5/13
 * @Describe
 */
public interface InputLoginView {

    void timerEnd();

    void loginSuccess(LoginBean successBean);

    void loginWx(LoginBean bean,String channel,String openid);
}
