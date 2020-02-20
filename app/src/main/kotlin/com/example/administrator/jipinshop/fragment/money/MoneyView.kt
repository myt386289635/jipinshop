package com.example.administrator.jipinshop.fragment.money

import com.example.administrator.jipinshop.bean.MoneyBean

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/19 11:41
 * Description： 红包页面接口
 */
interface MoneyView {
    fun onSuccess(bean : MoneyBean)
    fun onFile(error:String?)
}