package com.example.administrator.jipinshop.activity.info.member;

import com.example.administrator.jipinshop.bean.MemberLevelBean;

/**
 * @author 莫小婷
 * @create 2018/11/20
 * @Describe
 */
public interface MemberLevelView {
    void onSuccess(MemberLevelBean bean);
    void onFile(String error);
}
