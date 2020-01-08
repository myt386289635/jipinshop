package com.example.administrator.jipinshop.activity.newpeople;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewPeopleBean;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe
 */
public interface NewPeopleView {

    void onSuccess(NewPeopleBean bean);
    void onFile(String error);

    void onBuySuccess(ImageBean successBean);
    void onBuyFile(String error);
}
