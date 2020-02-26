package com.example.administrator.jipinshop.activity.money.binding

/**
 * @author 莫小婷
 * @create 2020/2/24
 * @Describe
 */
interface MoneyBindView {
    fun onSuccess(name: String?)
    fun onFile(error:String?)
    fun binding()
    fun info(info : String)
}