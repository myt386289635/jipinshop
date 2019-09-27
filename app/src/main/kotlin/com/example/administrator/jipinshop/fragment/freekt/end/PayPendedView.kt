package com.example.administrator.jipinshop.fragment.freekt.end

import com.example.administrator.jipinshop.bean.MyFreeBean

/**
 * @author 莫小婷
 * @create 2019/9/27
 * @Describe
 */
interface PayPendedView {
    fun onSuccess(bean: MyFreeBean)
    fun onFile(error: String?)
}