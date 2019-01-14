package com.example.administrator.jipinshop.activity.message;

import com.example.administrator.jipinshop.bean.SystemMessageBean;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe
 */
public interface MessageView {

    void Success(SystemMessageBean systemMessageBean);
    void Faile(String error);
}
