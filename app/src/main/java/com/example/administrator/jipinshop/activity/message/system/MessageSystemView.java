package com.example.administrator.jipinshop.activity.message.system;

import com.example.administrator.jipinshop.bean.MessageAllBean;

/**
 * @author 莫小婷
 * @create 2021/4/1
 * @Describe
 */
public interface MessageSystemView {

    void onSuccess(MessageAllBean bean);
    void onFile(String error);
}
