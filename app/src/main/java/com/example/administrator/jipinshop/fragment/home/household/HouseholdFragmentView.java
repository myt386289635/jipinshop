package com.example.administrator.jipinshop.fragment.home.household;

import com.example.administrator.jipinshop.bean.HouseholdFragmentBean;

/**
 * @author 莫小婷
 * @create 2018/10/27
 * @Describe
 */
public interface HouseholdFragmentView {

    void onSuccess(HouseholdFragmentBean householdFragmentBean);
    void onFile(String error);
}
