package com.example.administrator.jipinshop.activity.minekt.recovery

import com.example.administrator.jipinshop.bean.TbOrderBean

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
interface OrderRecoveryResultView {

    fun onSuccess(bean : TbOrderBean)
    fun onFile(error : String?)

    fun onFind()
}
