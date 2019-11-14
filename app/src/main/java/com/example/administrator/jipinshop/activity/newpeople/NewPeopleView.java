package com.example.administrator.jipinshop.activity.newpeople;

import com.example.administrator.jipinshop.bean.V2FreeListBean;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe
 */
public interface NewPeopleView {

    void onSuccess(V2FreeListBean bean);
    void onFile(String error);
}
