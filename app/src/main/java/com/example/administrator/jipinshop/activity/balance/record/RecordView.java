package com.example.administrator.jipinshop.activity.balance.record;

import com.example.administrator.jipinshop.bean.RecordBean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public interface RecordView {

    void SuccessRecord(RecordBean recordBean);

    void FaileRecord(String error);
}
