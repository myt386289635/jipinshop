package com.example.administrator.jipinshop.activity.minekt.welfare

import com.example.administrator.jipinshop.bean.WelfareBean

/**
 * @author 莫小婷
 * @create 2020/1/9
 * @Describe
 */
interface WelfareView {
    fun onSuccess(bean: WelfareBean)
    fun onFile(error: String?)
}