package com.example.administrator.jipinshop.activity.money.record

import com.example.administrator.jipinshop.bean.MoneyRecordBean

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe
 */
interface MoneyRecordView {

    fun onSuccess(bean: MoneyRecordBean)
    fun onFile(error:String?)

}