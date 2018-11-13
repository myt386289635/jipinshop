package com.example.administrator.jipinshop.activity.message.system;

import com.example.administrator.jipinshop.bean.SystemMessageBean;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe
 */
public interface SystemMessageView {

    void Success(SystemMessageBean systemMessageBean);
    void Faile(String error);
}
