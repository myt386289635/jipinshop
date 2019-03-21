package com.example.administrator.jipinshop.fragment.tryout;

import com.example.administrator.jipinshop.bean.TryBean;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe
 */
public interface TryView {
    void onSuccess(TryBean bean);
    void onFile(String error);
}
