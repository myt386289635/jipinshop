package com.example.administrator.jipinshop.activity.member.family;

import com.example.administrator.jipinshop.bean.FamilyBean;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public interface FamilyView {

    void onSuccess(FamilyBean bean);
    void onFile(String error);

    void onConfirm(int position);
}
