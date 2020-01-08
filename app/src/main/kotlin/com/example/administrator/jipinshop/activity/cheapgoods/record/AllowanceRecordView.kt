package com.example.administrator.jipinshop.activity.cheapgoods.record

import com.example.administrator.jipinshop.bean.AllowanceRecordBean

/**
 * @author 莫小婷
 * @create 2020/1/7
 * @Describe
 */
interface AllowanceRecordView {
    fun onSuccess(bean: AllowanceRecordBean)
    fun onFile(error: String?)
}