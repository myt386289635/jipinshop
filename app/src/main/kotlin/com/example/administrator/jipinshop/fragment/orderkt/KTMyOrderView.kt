package com.example.administrator.jipinshop.fragment.orderkt

import com.example.administrator.jipinshop.bean.OrderTBBean

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe
 */
interface KTMyOrderView {
    fun onSuccess(bean: OrderTBBean)
    fun onFile(error:String?)

    fun onNext(position : Int)
    fun onCommonFile(error:String?)
}