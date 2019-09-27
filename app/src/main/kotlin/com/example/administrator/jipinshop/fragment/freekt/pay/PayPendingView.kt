package com.example.administrator.jipinshop.fragment.freekt.pay

import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.MyFreeBean

/**
 * @author 莫小婷
 * @create 2019/9/26
 * @Describe
 */
interface PayPendingView {
    fun onSuccess(bean: MyFreeBean)
    fun onFile(error: String?)

    fun onBuyLinkSuccess(bean: ImageBean)
    fun onCommenFile(error: String?)
}