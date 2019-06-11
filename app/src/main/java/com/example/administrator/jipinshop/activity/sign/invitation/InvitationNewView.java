package com.example.administrator.jipinshop.activity.sign.invitation;

import com.example.administrator.jipinshop.bean.InvitationBean;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public interface InvitationNewView {
    void onSuccess(InvitationBean bean);
    void onFile(String error);
}
