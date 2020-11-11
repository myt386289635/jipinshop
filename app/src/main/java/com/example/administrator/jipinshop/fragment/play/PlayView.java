package com.example.administrator.jipinshop.fragment.play;

import com.example.administrator.jipinshop.bean.PlayBean;

/**
 * @author 莫小婷
 * @create 2020/11/11
 * @Describe
 */
public interface PlayView {

    void onSuccess(PlayBean bean);
    void onFile(String error);
}
