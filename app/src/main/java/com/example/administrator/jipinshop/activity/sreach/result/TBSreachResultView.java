package com.example.administrator.jipinshop.activity.sreach.result;

import com.example.administrator.jipinshop.bean.TBSreachResultBean;

/**
 * @author 莫小婷
 * @create 2019/12/3
 * @Describe
 */
public interface TBSreachResultView {

    void onSuccess(TBSreachResultBean bean);
    void onFile(String error);
}
