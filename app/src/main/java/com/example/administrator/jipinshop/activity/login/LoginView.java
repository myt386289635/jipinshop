package com.example.administrator.jipinshop.activity.login;

import com.example.administrator.jipinshop.bean.LoginBean;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public interface LoginView {

    void loginWx(LoginBean bean,String channel,String openid);

    void onSuccess(LoginBean loginBean);
}
