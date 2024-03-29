package com.example.administrator.jipinshop.fragment.member;

import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;

/**
 * @author 莫小婷
 * @create 2020/8/26
 * @Describe
 */
public interface MemberView {

    void onSuccess(MemberNewBean bean);
    void onFile(String error);

    void onCommenFile(String error);
    void initShare(ShareInfoBean bean);
}
