package com.example.administrator.jipinshop.fragment.foval;

import com.example.administrator.jipinshop.bean.FovalBean;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public interface FovalView {

    void FovalSuccess(FovalBean fovalBean);

    void FovalFaileNet(String throwable);

}
