package com.example.administrator.jipinshop.fragment.balance.budget;

import com.example.administrator.jipinshop.bean.BudgetDetailBean;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public interface BudgetDetailView {

    void onSuccess(BudgetDetailBean bean);
    void onFile(String error);
}
