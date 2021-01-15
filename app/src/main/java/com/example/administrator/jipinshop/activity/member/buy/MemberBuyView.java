package com.example.administrator.jipinshop.activity.member.buy;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
import com.example.administrator.jipinshop.bean.WxPayBean;

/**
 * @author 莫小婷
 * @create 2020/12/15
 * @Describe
 */
public interface MemberBuyView {

    void onSuccess(MemberBuyBean bean);

    void onPoint();
    void onWxPay(WxPayBean bean);
    void onAlipay(ImageBean bean);
    void onCommenFile(String error);

    void onInit(String userLevel);
}
